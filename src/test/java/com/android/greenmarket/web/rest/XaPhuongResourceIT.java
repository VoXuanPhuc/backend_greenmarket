package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.XaPhuong;
import com.android.greenmarket.repository.XaPhuongRepository;
import com.android.greenmarket.service.dto.XaPhuongDTO;
import com.android.greenmarket.service.mapper.XaPhuongMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link XaPhuongResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class XaPhuongResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/xa-phuongs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private XaPhuongRepository xaPhuongRepository;

    @Autowired
    private XaPhuongMapper xaPhuongMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restXaPhuongMockMvc;

    private XaPhuong xaPhuong;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static XaPhuong createEntity(EntityManager em) {
        XaPhuong xaPhuong = new XaPhuong().ten(DEFAULT_TEN);
        return xaPhuong;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static XaPhuong createUpdatedEntity(EntityManager em) {
        XaPhuong xaPhuong = new XaPhuong().ten(UPDATED_TEN);
        return xaPhuong;
    }

    @BeforeEach
    public void initTest() {
        xaPhuong = createEntity(em);
    }

    @Test
    @Transactional
    void createXaPhuong() throws Exception {
        int databaseSizeBeforeCreate = xaPhuongRepository.findAll().size();
        // Create the XaPhuong
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);
        restXaPhuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO)))
            .andExpect(status().isCreated());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeCreate + 1);
        XaPhuong testXaPhuong = xaPhuongList.get(xaPhuongList.size() - 1);
        assertThat(testXaPhuong.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void createXaPhuongWithExistingId() throws Exception {
        // Create the XaPhuong with an existing ID
        xaPhuong.setId(1L);
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);

        int databaseSizeBeforeCreate = xaPhuongRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restXaPhuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO)))
            .andExpect(status().isBadRequest());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllXaPhuongs() throws Exception {
        // Initialize the database
        xaPhuongRepository.saveAndFlush(xaPhuong);

        // Get all the xaPhuongList
        restXaPhuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(xaPhuong.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)));
    }

    @Test
    @Transactional
    void getXaPhuong() throws Exception {
        // Initialize the database
        xaPhuongRepository.saveAndFlush(xaPhuong);

        // Get the xaPhuong
        restXaPhuongMockMvc
            .perform(get(ENTITY_API_URL_ID, xaPhuong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(xaPhuong.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN));
    }

    @Test
    @Transactional
    void getNonExistingXaPhuong() throws Exception {
        // Get the xaPhuong
        restXaPhuongMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewXaPhuong() throws Exception {
        // Initialize the database
        xaPhuongRepository.saveAndFlush(xaPhuong);

        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();

        // Update the xaPhuong
        XaPhuong updatedXaPhuong = xaPhuongRepository.findById(xaPhuong.getId()).get();
        // Disconnect from session so that the updates on updatedXaPhuong are not directly saved in db
        em.detach(updatedXaPhuong);
        updatedXaPhuong.ten(UPDATED_TEN);
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(updatedXaPhuong);

        restXaPhuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, xaPhuongDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO))
            )
            .andExpect(status().isOk());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
        XaPhuong testXaPhuong = xaPhuongList.get(xaPhuongList.size() - 1);
        assertThat(testXaPhuong.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void putNonExistingXaPhuong() throws Exception {
        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();
        xaPhuong.setId(count.incrementAndGet());

        // Create the XaPhuong
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restXaPhuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, xaPhuongDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchXaPhuong() throws Exception {
        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();
        xaPhuong.setId(count.incrementAndGet());

        // Create the XaPhuong
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restXaPhuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamXaPhuong() throws Exception {
        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();
        xaPhuong.setId(count.incrementAndGet());

        // Create the XaPhuong
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restXaPhuongMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateXaPhuongWithPatch() throws Exception {
        // Initialize the database
        xaPhuongRepository.saveAndFlush(xaPhuong);

        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();

        // Update the xaPhuong using partial update
        XaPhuong partialUpdatedXaPhuong = new XaPhuong();
        partialUpdatedXaPhuong.setId(xaPhuong.getId());

        partialUpdatedXaPhuong.ten(UPDATED_TEN);

        restXaPhuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedXaPhuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedXaPhuong))
            )
            .andExpect(status().isOk());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
        XaPhuong testXaPhuong = xaPhuongList.get(xaPhuongList.size() - 1);
        assertThat(testXaPhuong.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void fullUpdateXaPhuongWithPatch() throws Exception {
        // Initialize the database
        xaPhuongRepository.saveAndFlush(xaPhuong);

        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();

        // Update the xaPhuong using partial update
        XaPhuong partialUpdatedXaPhuong = new XaPhuong();
        partialUpdatedXaPhuong.setId(xaPhuong.getId());

        partialUpdatedXaPhuong.ten(UPDATED_TEN);

        restXaPhuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedXaPhuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedXaPhuong))
            )
            .andExpect(status().isOk());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
        XaPhuong testXaPhuong = xaPhuongList.get(xaPhuongList.size() - 1);
        assertThat(testXaPhuong.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void patchNonExistingXaPhuong() throws Exception {
        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();
        xaPhuong.setId(count.incrementAndGet());

        // Create the XaPhuong
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restXaPhuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, xaPhuongDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchXaPhuong() throws Exception {
        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();
        xaPhuong.setId(count.incrementAndGet());

        // Create the XaPhuong
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restXaPhuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamXaPhuong() throws Exception {
        int databaseSizeBeforeUpdate = xaPhuongRepository.findAll().size();
        xaPhuong.setId(count.incrementAndGet());

        // Create the XaPhuong
        XaPhuongDTO xaPhuongDTO = xaPhuongMapper.toDto(xaPhuong);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restXaPhuongMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(xaPhuongDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the XaPhuong in the database
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteXaPhuong() throws Exception {
        // Initialize the database
        xaPhuongRepository.saveAndFlush(xaPhuong);

        int databaseSizeBeforeDelete = xaPhuongRepository.findAll().size();

        // Delete the xaPhuong
        restXaPhuongMockMvc
            .perform(delete(ENTITY_API_URL_ID, xaPhuong.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<XaPhuong> xaPhuongList = xaPhuongRepository.findAll();
        assertThat(xaPhuongList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
