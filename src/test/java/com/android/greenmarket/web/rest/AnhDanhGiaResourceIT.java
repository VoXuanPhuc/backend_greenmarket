package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.AnhDanhGia;
import com.android.greenmarket.repository.AnhDanhGiaRepository;
import com.android.greenmarket.service.dto.AnhDanhGiaDTO;
import com.android.greenmarket.service.mapper.AnhDanhGiaMapper;
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
 * Integration tests for the {@link AnhDanhGiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnhDanhGiaResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anh-danh-gias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnhDanhGiaRepository anhDanhGiaRepository;

    @Autowired
    private AnhDanhGiaMapper anhDanhGiaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnhDanhGiaMockMvc;

    private AnhDanhGia anhDanhGia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnhDanhGia createEntity(EntityManager em) {
        AnhDanhGia anhDanhGia = new AnhDanhGia().ten(DEFAULT_TEN);
        return anhDanhGia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnhDanhGia createUpdatedEntity(EntityManager em) {
        AnhDanhGia anhDanhGia = new AnhDanhGia().ten(UPDATED_TEN);
        return anhDanhGia;
    }

    @BeforeEach
    public void initTest() {
        anhDanhGia = createEntity(em);
    }

    @Test
    @Transactional
    void createAnhDanhGia() throws Exception {
        int databaseSizeBeforeCreate = anhDanhGiaRepository.findAll().size();
        // Create the AnhDanhGia
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);
        restAnhDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO)))
            .andExpect(status().isCreated());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeCreate + 1);
        AnhDanhGia testAnhDanhGia = anhDanhGiaList.get(anhDanhGiaList.size() - 1);
        assertThat(testAnhDanhGia.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void createAnhDanhGiaWithExistingId() throws Exception {
        // Create the AnhDanhGia with an existing ID
        anhDanhGia.setId(1L);
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        int databaseSizeBeforeCreate = anhDanhGiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnhDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = anhDanhGiaRepository.findAll().size();
        // set the field null
        anhDanhGia.setTen(null);

        // Create the AnhDanhGia, which fails.
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        restAnhDanhGiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO)))
            .andExpect(status().isBadRequest());

        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnhDanhGias() throws Exception {
        // Initialize the database
        anhDanhGiaRepository.saveAndFlush(anhDanhGia);

        // Get all the anhDanhGiaList
        restAnhDanhGiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anhDanhGia.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)));
    }

    @Test
    @Transactional
    void getAnhDanhGia() throws Exception {
        // Initialize the database
        anhDanhGiaRepository.saveAndFlush(anhDanhGia);

        // Get the anhDanhGia
        restAnhDanhGiaMockMvc
            .perform(get(ENTITY_API_URL_ID, anhDanhGia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anhDanhGia.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN));
    }

    @Test
    @Transactional
    void getNonExistingAnhDanhGia() throws Exception {
        // Get the anhDanhGia
        restAnhDanhGiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnhDanhGia() throws Exception {
        // Initialize the database
        anhDanhGiaRepository.saveAndFlush(anhDanhGia);

        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();

        // Update the anhDanhGia
        AnhDanhGia updatedAnhDanhGia = anhDanhGiaRepository.findById(anhDanhGia.getId()).get();
        // Disconnect from session so that the updates on updatedAnhDanhGia are not directly saved in db
        em.detach(updatedAnhDanhGia);
        updatedAnhDanhGia.ten(UPDATED_TEN);
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(updatedAnhDanhGia);

        restAnhDanhGiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anhDanhGiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
        AnhDanhGia testAnhDanhGia = anhDanhGiaList.get(anhDanhGiaList.size() - 1);
        assertThat(testAnhDanhGia.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void putNonExistingAnhDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();
        anhDanhGia.setId(count.incrementAndGet());

        // Create the AnhDanhGia
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnhDanhGiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anhDanhGiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnhDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();
        anhDanhGia.setId(count.incrementAndGet());

        // Create the AnhDanhGia
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhDanhGiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnhDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();
        anhDanhGia.setId(count.incrementAndGet());

        // Create the AnhDanhGia
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhDanhGiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnhDanhGiaWithPatch() throws Exception {
        // Initialize the database
        anhDanhGiaRepository.saveAndFlush(anhDanhGia);

        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();

        // Update the anhDanhGia using partial update
        AnhDanhGia partialUpdatedAnhDanhGia = new AnhDanhGia();
        partialUpdatedAnhDanhGia.setId(anhDanhGia.getId());

        partialUpdatedAnhDanhGia.ten(UPDATED_TEN);

        restAnhDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnhDanhGia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnhDanhGia))
            )
            .andExpect(status().isOk());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
        AnhDanhGia testAnhDanhGia = anhDanhGiaList.get(anhDanhGiaList.size() - 1);
        assertThat(testAnhDanhGia.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void fullUpdateAnhDanhGiaWithPatch() throws Exception {
        // Initialize the database
        anhDanhGiaRepository.saveAndFlush(anhDanhGia);

        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();

        // Update the anhDanhGia using partial update
        AnhDanhGia partialUpdatedAnhDanhGia = new AnhDanhGia();
        partialUpdatedAnhDanhGia.setId(anhDanhGia.getId());

        partialUpdatedAnhDanhGia.ten(UPDATED_TEN);

        restAnhDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnhDanhGia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnhDanhGia))
            )
            .andExpect(status().isOk());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
        AnhDanhGia testAnhDanhGia = anhDanhGiaList.get(anhDanhGiaList.size() - 1);
        assertThat(testAnhDanhGia.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void patchNonExistingAnhDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();
        anhDanhGia.setId(count.incrementAndGet());

        // Create the AnhDanhGia
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnhDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anhDanhGiaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnhDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();
        anhDanhGia.setId(count.incrementAndGet());

        // Create the AnhDanhGia
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnhDanhGia() throws Exception {
        int databaseSizeBeforeUpdate = anhDanhGiaRepository.findAll().size();
        anhDanhGia.setId(count.incrementAndGet());

        // Create the AnhDanhGia
        AnhDanhGiaDTO anhDanhGiaDTO = anhDanhGiaMapper.toDto(anhDanhGia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhDanhGiaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(anhDanhGiaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnhDanhGia in the database
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnhDanhGia() throws Exception {
        // Initialize the database
        anhDanhGiaRepository.saveAndFlush(anhDanhGia);

        int databaseSizeBeforeDelete = anhDanhGiaRepository.findAll().size();

        // Delete the anhDanhGia
        restAnhDanhGiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, anhDanhGia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnhDanhGia> anhDanhGiaList = anhDanhGiaRepository.findAll();
        assertThat(anhDanhGiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
