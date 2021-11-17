package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.DanhGia;
import com.android.greenmarket.repository.DanhGiaRepository;
import com.android.greenmarket.service.dto.DanhGiaDTO;
import com.android.greenmarket.service.mapper.DanhGiaMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link DanhGiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DanhGiaResourceIT {

    private static final Integer DEFAULT_SAO = 1;
    private static final Integer UPDATED_SAO = 2;

    private static final String DEFAULT_CHITIET = "AAAAAAAAAA";
    private static final String UPDATED_CHITIET = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAYDANHGIA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAYDANHGIA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/danh-gias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @Autowired
    private DanhGiaMapper danhGiaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDanhGiaMockMvc;

    private DanhGia danhGia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DanhGia createEntity(EntityManager em) {
        DanhGia danhGia = new DanhGia().sao(DEFAULT_SAO).chitiet(DEFAULT_CHITIET).image(DEFAULT_IMAGE).ngaydanhgia(DEFAULT_NGAYDANHGIA);
        return danhGia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DanhGia createUpdatedEntity(EntityManager em) {
        DanhGia danhGia = new DanhGia().sao(UPDATED_SAO).chitiet(UPDATED_CHITIET).image(UPDATED_IMAGE).ngaydanhgia(UPDATED_NGAYDANHGIA);
        return danhGia;
    }

    @BeforeEach
    public void initTest() {
        danhGia = createEntity(em);
    }

    @Test
    @Transactional
    void createDanhGia() throws Exception {
        int databaseSizeBeforeCreate = danhGiaRepository.findAll().size();
        // Create the DanhGia
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);
        restDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhGiaDTO)))
            .andExpect(status().isCreated());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeCreate + 1);
        DanhGia testDanhGia = danhGiaList.get(danhGiaList.size() - 1);
        assertThat(testDanhGia.getSao()).isEqualTo(DEFAULT_SAO);
        assertThat(testDanhGia.getChitiet()).isEqualTo(DEFAULT_CHITIET);
        assertThat(testDanhGia.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDanhGia.getNgaydanhgia()).isEqualTo(DEFAULT_NGAYDANHGIA);
    }

    @Test
    @Transactional
    void createDanhGiaWithExistingId() throws Exception {
        // Create the DanhGia with an existing ID
        danhGia.setId(1L);
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        int databaseSizeBeforeCreate = danhGiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhGiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = danhGiaRepository.findAll().size();
        // set the field null
        danhGia.setSao(null);

        // Create the DanhGia, which fails.
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        restDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhGiaDTO)))
            .andExpect(status().isBadRequest());

        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChitietIsRequired() throws Exception {
        int databaseSizeBeforeTest = danhGiaRepository.findAll().size();
        // set the field null
        danhGia.setChitiet(null);

        // Create the DanhGia, which fails.
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        restDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhGiaDTO)))
            .andExpect(status().isBadRequest());

        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = danhGiaRepository.findAll().size();
        // set the field null
        danhGia.setImage(null);

        // Create the DanhGia, which fails.
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        restDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhGiaDTO)))
            .andExpect(status().isBadRequest());

        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgaydanhgiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = danhGiaRepository.findAll().size();
        // set the field null
        danhGia.setNgaydanhgia(null);

        // Create the DanhGia, which fails.
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        restDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhGiaDTO)))
            .andExpect(status().isBadRequest());

        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDanhGias() throws Exception {
        // Initialize the database
        danhGiaRepository.saveAndFlush(danhGia);

        // Get all the danhGiaList
        restDanhGiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhGia.getId().intValue())))
            .andExpect(jsonPath("$.[*].sao").value(hasItem(DEFAULT_SAO)))
            .andExpect(jsonPath("$.[*].chitiet").value(hasItem(DEFAULT_CHITIET)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].ngaydanhgia").value(hasItem(DEFAULT_NGAYDANHGIA.toString())));
    }

    @Test
    @Transactional
    void getDanhGia() throws Exception {
        // Initialize the database
        danhGiaRepository.saveAndFlush(danhGia);

        // Get the danhGia
        restDanhGiaMockMvc
            .perform(get(ENTITY_API_URL_ID, danhGia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(danhGia.getId().intValue()))
            .andExpect(jsonPath("$.sao").value(DEFAULT_SAO))
            .andExpect(jsonPath("$.chitiet").value(DEFAULT_CHITIET))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.ngaydanhgia").value(DEFAULT_NGAYDANHGIA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDanhGia() throws Exception {
        // Get the danhGia
        restDanhGiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDanhGia() throws Exception {
        // Initialize the database
        danhGiaRepository.saveAndFlush(danhGia);

        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();

        // Update the danhGia
        DanhGia updatedDanhGia = danhGiaRepository.findById(danhGia.getId()).get();
        // Disconnect from session so that the updates on updatedDanhGia are not directly saved in db
        em.detach(updatedDanhGia);
        updatedDanhGia.sao(UPDATED_SAO).chitiet(UPDATED_CHITIET).image(UPDATED_IMAGE).ngaydanhgia(UPDATED_NGAYDANHGIA);
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(updatedDanhGia);

        restDanhGiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, danhGiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(danhGiaDTO))
            )
            .andExpect(status().isOk());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
        DanhGia testDanhGia = danhGiaList.get(danhGiaList.size() - 1);
        assertThat(testDanhGia.getSao()).isEqualTo(UPDATED_SAO);
        assertThat(testDanhGia.getChitiet()).isEqualTo(UPDATED_CHITIET);
        assertThat(testDanhGia.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDanhGia.getNgaydanhgia()).isEqualTo(UPDATED_NGAYDANHGIA);
    }

    @Test
    @Transactional
    void putNonExistingDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();
        danhGia.setId(count.incrementAndGet());

        // Create the DanhGia
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhGiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, danhGiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(danhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();
        danhGia.setId(count.incrementAndGet());

        // Create the DanhGia
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhGiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(danhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();
        danhGia.setId(count.incrementAndGet());

        // Create the DanhGia
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhGiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(danhGiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDanhGiaWithPatch() throws Exception {
        // Initialize the database
        danhGiaRepository.saveAndFlush(danhGia);

        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();

        // Update the danhGia using partial update
        DanhGia partialUpdatedDanhGia = new DanhGia();
        partialUpdatedDanhGia.setId(danhGia.getId());

        restDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDanhGia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDanhGia))
            )
            .andExpect(status().isOk());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
        DanhGia testDanhGia = danhGiaList.get(danhGiaList.size() - 1);
        assertThat(testDanhGia.getSao()).isEqualTo(DEFAULT_SAO);
        assertThat(testDanhGia.getChitiet()).isEqualTo(DEFAULT_CHITIET);
        assertThat(testDanhGia.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDanhGia.getNgaydanhgia()).isEqualTo(DEFAULT_NGAYDANHGIA);
    }

    @Test
    @Transactional
    void fullUpdateDanhGiaWithPatch() throws Exception {
        // Initialize the database
        danhGiaRepository.saveAndFlush(danhGia);

        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();

        // Update the danhGia using partial update
        DanhGia partialUpdatedDanhGia = new DanhGia();
        partialUpdatedDanhGia.setId(danhGia.getId());

        partialUpdatedDanhGia.sao(UPDATED_SAO).chitiet(UPDATED_CHITIET).image(UPDATED_IMAGE).ngaydanhgia(UPDATED_NGAYDANHGIA);

        restDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDanhGia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDanhGia))
            )
            .andExpect(status().isOk());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
        DanhGia testDanhGia = danhGiaList.get(danhGiaList.size() - 1);
        assertThat(testDanhGia.getSao()).isEqualTo(UPDATED_SAO);
        assertThat(testDanhGia.getChitiet()).isEqualTo(UPDATED_CHITIET);
        assertThat(testDanhGia.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDanhGia.getNgaydanhgia()).isEqualTo(UPDATED_NGAYDANHGIA);
    }

    @Test
    @Transactional
    void patchNonExistingDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();
        danhGia.setId(count.incrementAndGet());

        // Create the DanhGia
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, danhGiaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(danhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();
        danhGia.setId(count.incrementAndGet());

        // Create the DanhGia
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(danhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = danhGiaRepository.findAll().size();
        danhGia.setId(count.incrementAndGet());

        // Create the DanhGia
        DanhGiaDTO danhGiaDTO = danhGiaMapper.toDto(danhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(danhGiaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DanhGia in the database
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDanhGia() throws Exception {
        // Initialize the database
        danhGiaRepository.saveAndFlush(danhGia);

        int databaseSizeBeforeDelete = danhGiaRepository.findAll().size();

        // Delete the danhGia
        restDanhGiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, danhGia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DanhGia> danhGiaList = danhGiaRepository.findAll();
        assertThat(danhGiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
