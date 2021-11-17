package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.TinhTP;
import com.android.greenmarket.repository.TinhTPRepository;
import com.android.greenmarket.service.dto.TinhTPDTO;
import com.android.greenmarket.service.mapper.TinhTPMapper;
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
 * Integration tests for the {@link TinhTPResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TinhTPResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tinh-tps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TinhTPRepository tinhTPRepository;

    @Autowired
    private TinhTPMapper tinhTPMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTinhTPMockMvc;

    private TinhTP tinhTP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TinhTP createEntity(EntityManager em) {
        TinhTP tinhTP = new TinhTP().ten(DEFAULT_TEN);
        return tinhTP;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TinhTP createUpdatedEntity(EntityManager em) {
        TinhTP tinhTP = new TinhTP().ten(UPDATED_TEN);
        return tinhTP;
    }

    @BeforeEach
    public void initTest() {
        tinhTP = createEntity(em);
    }

    @Test
    @Transactional
    void createTinhTP() throws Exception {
        int databaseSizeBeforeCreate = tinhTPRepository.findAll().size();
        // Create the TinhTP
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);
        restTinhTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tinhTPDTO)))
            .andExpect(status().isCreated());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeCreate + 1);
        TinhTP testTinhTP = tinhTPList.get(tinhTPList.size() - 1);
        assertThat(testTinhTP.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void createTinhTPWithExistingId() throws Exception {
        // Create the TinhTP with an existing ID
        tinhTP.setId(1L);
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);

        int databaseSizeBeforeCreate = tinhTPRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTinhTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tinhTPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTinhTPS() throws Exception {
        // Initialize the database
        tinhTPRepository.saveAndFlush(tinhTP);

        // Get all the tinhTPList
        restTinhTPMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tinhTP.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)));
    }

    @Test
    @Transactional
    void getTinhTP() throws Exception {
        // Initialize the database
        tinhTPRepository.saveAndFlush(tinhTP);

        // Get the tinhTP
        restTinhTPMockMvc
            .perform(get(ENTITY_API_URL_ID, tinhTP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tinhTP.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN));
    }

    @Test
    @Transactional
    void getNonExistingTinhTP() throws Exception {
        // Get the tinhTP
        restTinhTPMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTinhTP() throws Exception {
        // Initialize the database
        tinhTPRepository.saveAndFlush(tinhTP);

        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();

        // Update the tinhTP
        TinhTP updatedTinhTP = tinhTPRepository.findById(tinhTP.getId()).get();
        // Disconnect from session so that the updates on updatedTinhTP are not directly saved in db
        em.detach(updatedTinhTP);
        updatedTinhTP.ten(UPDATED_TEN);
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(updatedTinhTP);

        restTinhTPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tinhTPDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tinhTPDTO))
            )
            .andExpect(status().isOk());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
        TinhTP testTinhTP = tinhTPList.get(tinhTPList.size() - 1);
        assertThat(testTinhTP.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void putNonExistingTinhTP() throws Exception {
        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();
        tinhTP.setId(count.incrementAndGet());

        // Create the TinhTP
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTinhTPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tinhTPDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tinhTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTinhTP() throws Exception {
        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();
        tinhTP.setId(count.incrementAndGet());

        // Create the TinhTP
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTinhTPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tinhTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTinhTP() throws Exception {
        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();
        tinhTP.setId(count.incrementAndGet());

        // Create the TinhTP
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTinhTPMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tinhTPDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTinhTPWithPatch() throws Exception {
        // Initialize the database
        tinhTPRepository.saveAndFlush(tinhTP);

        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();

        // Update the tinhTP using partial update
        TinhTP partialUpdatedTinhTP = new TinhTP();
        partialUpdatedTinhTP.setId(tinhTP.getId());

        partialUpdatedTinhTP.ten(UPDATED_TEN);

        restTinhTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTinhTP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTinhTP))
            )
            .andExpect(status().isOk());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
        TinhTP testTinhTP = tinhTPList.get(tinhTPList.size() - 1);
        assertThat(testTinhTP.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void fullUpdateTinhTPWithPatch() throws Exception {
        // Initialize the database
        tinhTPRepository.saveAndFlush(tinhTP);

        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();

        // Update the tinhTP using partial update
        TinhTP partialUpdatedTinhTP = new TinhTP();
        partialUpdatedTinhTP.setId(tinhTP.getId());

        partialUpdatedTinhTP.ten(UPDATED_TEN);

        restTinhTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTinhTP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTinhTP))
            )
            .andExpect(status().isOk());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
        TinhTP testTinhTP = tinhTPList.get(tinhTPList.size() - 1);
        assertThat(testTinhTP.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void patchNonExistingTinhTP() throws Exception {
        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();
        tinhTP.setId(count.incrementAndGet());

        // Create the TinhTP
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTinhTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tinhTPDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tinhTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTinhTP() throws Exception {
        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();
        tinhTP.setId(count.incrementAndGet());

        // Create the TinhTP
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTinhTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tinhTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTinhTP() throws Exception {
        int databaseSizeBeforeUpdate = tinhTPRepository.findAll().size();
        tinhTP.setId(count.incrementAndGet());

        // Create the TinhTP
        TinhTPDTO tinhTPDTO = tinhTPMapper.toDto(tinhTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTinhTPMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tinhTPDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TinhTP in the database
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTinhTP() throws Exception {
        // Initialize the database
        tinhTPRepository.saveAndFlush(tinhTP);

        int databaseSizeBeforeDelete = tinhTPRepository.findAll().size();

        // Delete the tinhTP
        restTinhTPMockMvc
            .perform(delete(ENTITY_API_URL_ID, tinhTP.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TinhTP> tinhTPList = tinhTPRepository.findAll();
        assertThat(tinhTPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
