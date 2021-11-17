package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.HuyenQuan;
import com.android.greenmarket.repository.HuyenQuanRepository;
import com.android.greenmarket.service.dto.HuyenQuanDTO;
import com.android.greenmarket.service.mapper.HuyenQuanMapper;
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
 * Integration tests for the {@link HuyenQuanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HuyenQuanResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/huyen-quans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HuyenQuanRepository huyenQuanRepository;

    @Autowired
    private HuyenQuanMapper huyenQuanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHuyenQuanMockMvc;

    private HuyenQuan huyenQuan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HuyenQuan createEntity(EntityManager em) {
        HuyenQuan huyenQuan = new HuyenQuan().ten(DEFAULT_TEN);
        return huyenQuan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HuyenQuan createUpdatedEntity(EntityManager em) {
        HuyenQuan huyenQuan = new HuyenQuan().ten(UPDATED_TEN);
        return huyenQuan;
    }

    @BeforeEach
    public void initTest() {
        huyenQuan = createEntity(em);
    }

    @Test
    @Transactional
    void createHuyenQuan() throws Exception {
        int databaseSizeBeforeCreate = huyenQuanRepository.findAll().size();
        // Create the HuyenQuan
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);
        restHuyenQuanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO)))
            .andExpect(status().isCreated());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeCreate + 1);
        HuyenQuan testHuyenQuan = huyenQuanList.get(huyenQuanList.size() - 1);
        assertThat(testHuyenQuan.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void createHuyenQuanWithExistingId() throws Exception {
        // Create the HuyenQuan with an existing ID
        huyenQuan.setId(1L);
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);

        int databaseSizeBeforeCreate = huyenQuanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHuyenQuanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHuyenQuans() throws Exception {
        // Initialize the database
        huyenQuanRepository.saveAndFlush(huyenQuan);

        // Get all the huyenQuanList
        restHuyenQuanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(huyenQuan.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)));
    }

    @Test
    @Transactional
    void getHuyenQuan() throws Exception {
        // Initialize the database
        huyenQuanRepository.saveAndFlush(huyenQuan);

        // Get the huyenQuan
        restHuyenQuanMockMvc
            .perform(get(ENTITY_API_URL_ID, huyenQuan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(huyenQuan.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN));
    }

    @Test
    @Transactional
    void getNonExistingHuyenQuan() throws Exception {
        // Get the huyenQuan
        restHuyenQuanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHuyenQuan() throws Exception {
        // Initialize the database
        huyenQuanRepository.saveAndFlush(huyenQuan);

        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();

        // Update the huyenQuan
        HuyenQuan updatedHuyenQuan = huyenQuanRepository.findById(huyenQuan.getId()).get();
        // Disconnect from session so that the updates on updatedHuyenQuan are not directly saved in db
        em.detach(updatedHuyenQuan);
        updatedHuyenQuan.ten(UPDATED_TEN);
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(updatedHuyenQuan);

        restHuyenQuanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, huyenQuanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO))
            )
            .andExpect(status().isOk());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
        HuyenQuan testHuyenQuan = huyenQuanList.get(huyenQuanList.size() - 1);
        assertThat(testHuyenQuan.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void putNonExistingHuyenQuan() throws Exception {
        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();
        huyenQuan.setId(count.incrementAndGet());

        // Create the HuyenQuan
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHuyenQuanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, huyenQuanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHuyenQuan() throws Exception {
        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();
        huyenQuan.setId(count.incrementAndGet());

        // Create the HuyenQuan
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuyenQuanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHuyenQuan() throws Exception {
        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();
        huyenQuan.setId(count.incrementAndGet());

        // Create the HuyenQuan
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuyenQuanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHuyenQuanWithPatch() throws Exception {
        // Initialize the database
        huyenQuanRepository.saveAndFlush(huyenQuan);

        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();

        // Update the huyenQuan using partial update
        HuyenQuan partialUpdatedHuyenQuan = new HuyenQuan();
        partialUpdatedHuyenQuan.setId(huyenQuan.getId());

        restHuyenQuanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHuyenQuan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHuyenQuan))
            )
            .andExpect(status().isOk());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
        HuyenQuan testHuyenQuan = huyenQuanList.get(huyenQuanList.size() - 1);
        assertThat(testHuyenQuan.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void fullUpdateHuyenQuanWithPatch() throws Exception {
        // Initialize the database
        huyenQuanRepository.saveAndFlush(huyenQuan);

        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();

        // Update the huyenQuan using partial update
        HuyenQuan partialUpdatedHuyenQuan = new HuyenQuan();
        partialUpdatedHuyenQuan.setId(huyenQuan.getId());

        partialUpdatedHuyenQuan.ten(UPDATED_TEN);

        restHuyenQuanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHuyenQuan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHuyenQuan))
            )
            .andExpect(status().isOk());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
        HuyenQuan testHuyenQuan = huyenQuanList.get(huyenQuanList.size() - 1);
        assertThat(testHuyenQuan.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void patchNonExistingHuyenQuan() throws Exception {
        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();
        huyenQuan.setId(count.incrementAndGet());

        // Create the HuyenQuan
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHuyenQuanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, huyenQuanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHuyenQuan() throws Exception {
        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();
        huyenQuan.setId(count.incrementAndGet());

        // Create the HuyenQuan
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuyenQuanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHuyenQuan() throws Exception {
        int databaseSizeBeforeUpdate = huyenQuanRepository.findAll().size();
        huyenQuan.setId(count.incrementAndGet());

        // Create the HuyenQuan
        HuyenQuanDTO huyenQuanDTO = huyenQuanMapper.toDto(huyenQuan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuyenQuanMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(huyenQuanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HuyenQuan in the database
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHuyenQuan() throws Exception {
        // Initialize the database
        huyenQuanRepository.saveAndFlush(huyenQuan);

        int databaseSizeBeforeDelete = huyenQuanRepository.findAll().size();

        // Delete the huyenQuan
        restHuyenQuanMockMvc
            .perform(delete(ENTITY_API_URL_ID, huyenQuan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HuyenQuan> huyenQuanList = huyenQuanRepository.findAll();
        assertThat(huyenQuanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
