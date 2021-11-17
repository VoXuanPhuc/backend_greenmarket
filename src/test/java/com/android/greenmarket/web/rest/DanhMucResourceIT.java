package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.DanhMuc;
import com.android.greenmarket.repository.DanhMucRepository;
import com.android.greenmarket.service.dto.DanhMucDTO;
import com.android.greenmarket.service.mapper.DanhMucMapper;
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
 * Integration tests for the {@link DanhMucResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DanhMucResourceIT {

    private static final String DEFAULT_TEN_DM = "AAAAAAAAAA";
    private static final String UPDATED_TEN_DM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/danh-mucs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private DanhMucMapper danhMucMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDanhMucMockMvc;

    private DanhMuc danhMuc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DanhMuc createEntity(EntityManager em) {
        DanhMuc danhMuc = new DanhMuc().tenDM(DEFAULT_TEN_DM);
        return danhMuc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DanhMuc createUpdatedEntity(EntityManager em) {
        DanhMuc danhMuc = new DanhMuc().tenDM(UPDATED_TEN_DM);
        return danhMuc;
    }

    @BeforeEach
    public void initTest() {
        danhMuc = createEntity(em);
    }

    @Test
    @Transactional
    void createDanhMuc() throws Exception {
        int databaseSizeBeforeCreate = danhMucRepository.findAll().size();
        // Create the DanhMuc
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);
        restDanhMucMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhMucDTO)))
            .andExpect(status().isCreated());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeCreate + 1);
        DanhMuc testDanhMuc = danhMucList.get(danhMucList.size() - 1);
        assertThat(testDanhMuc.getTenDM()).isEqualTo(DEFAULT_TEN_DM);
    }

    @Test
    @Transactional
    void createDanhMucWithExistingId() throws Exception {
        // Create the DanhMuc with an existing ID
        danhMuc.setId(1L);
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);

        int databaseSizeBeforeCreate = danhMucRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDanhMucMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhMucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDanhMucs() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        // Get all the danhMucList
        restDanhMucMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhMuc.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenDM").value(hasItem(DEFAULT_TEN_DM)));
    }

    @Test
    @Transactional
    void getDanhMuc() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        // Get the danhMuc
        restDanhMucMockMvc
            .perform(get(ENTITY_API_URL_ID, danhMuc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(danhMuc.getId().intValue()))
            .andExpect(jsonPath("$.tenDM").value(DEFAULT_TEN_DM));
    }

    @Test
    @Transactional
    void getNonExistingDanhMuc() throws Exception {
        // Get the danhMuc
        restDanhMucMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDanhMuc() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();

        // Update the danhMuc
        DanhMuc updatedDanhMuc = danhMucRepository.findById(danhMuc.getId()).get();
        // Disconnect from session so that the updates on updatedDanhMuc are not directly saved in db
        em.detach(updatedDanhMuc);
        updatedDanhMuc.tenDM(UPDATED_TEN_DM);
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(updatedDanhMuc);

        restDanhMucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, danhMucDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(danhMucDTO))
            )
            .andExpect(status().isOk());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
        DanhMuc testDanhMuc = danhMucList.get(danhMucList.size() - 1);
        assertThat(testDanhMuc.getTenDM()).isEqualTo(UPDATED_TEN_DM);
    }

    @Test
    @Transactional
    void putNonExistingDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();
        danhMuc.setId(count.incrementAndGet());

        // Create the DanhMuc
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhMucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, danhMucDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(danhMucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();
        danhMuc.setId(count.incrementAndGet());

        // Create the DanhMuc
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhMucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(danhMucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();
        danhMuc.setId(count.incrementAndGet());

        // Create the DanhMuc
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhMucMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhMucDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDanhMucWithPatch() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();

        // Update the danhMuc using partial update
        DanhMuc partialUpdatedDanhMuc = new DanhMuc();
        partialUpdatedDanhMuc.setId(danhMuc.getId());

        restDanhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDanhMuc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDanhMuc))
            )
            .andExpect(status().isOk());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
        DanhMuc testDanhMuc = danhMucList.get(danhMucList.size() - 1);
        assertThat(testDanhMuc.getTenDM()).isEqualTo(DEFAULT_TEN_DM);
    }

    @Test
    @Transactional
    void fullUpdateDanhMucWithPatch() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();

        // Update the danhMuc using partial update
        DanhMuc partialUpdatedDanhMuc = new DanhMuc();
        partialUpdatedDanhMuc.setId(danhMuc.getId());

        partialUpdatedDanhMuc.tenDM(UPDATED_TEN_DM);

        restDanhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDanhMuc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDanhMuc))
            )
            .andExpect(status().isOk());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
        DanhMuc testDanhMuc = danhMucList.get(danhMucList.size() - 1);
        assertThat(testDanhMuc.getTenDM()).isEqualTo(UPDATED_TEN_DM);
    }

    @Test
    @Transactional
    void patchNonExistingDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();
        danhMuc.setId(count.incrementAndGet());

        // Create the DanhMuc
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, danhMucDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(danhMucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();
        danhMuc.setId(count.incrementAndGet());

        // Create the DanhMuc
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(danhMucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();
        danhMuc.setId(count.incrementAndGet());

        // Create the DanhMuc
        DanhMucDTO danhMucDTO = danhMucMapper.toDto(danhMuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhMucMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(danhMucDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDanhMuc() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        int databaseSizeBeforeDelete = danhMucRepository.findAll().size();

        // Delete the danhMuc
        restDanhMucMockMvc
            .perform(delete(ENTITY_API_URL_ID, danhMuc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
