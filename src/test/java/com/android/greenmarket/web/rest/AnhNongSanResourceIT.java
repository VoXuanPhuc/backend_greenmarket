package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.AnhNongSan;
import com.android.greenmarket.repository.AnhNongSanRepository;
import com.android.greenmarket.service.dto.AnhNongSanDTO;
import com.android.greenmarket.service.mapper.AnhNongSanMapper;
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
 * Integration tests for the {@link AnhNongSanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnhNongSanResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anh-nong-sans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnhNongSanRepository anhNongSanRepository;

    @Autowired
    private AnhNongSanMapper anhNongSanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnhNongSanMockMvc;

    private AnhNongSan anhNongSan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnhNongSan createEntity(EntityManager em) {
        AnhNongSan anhNongSan = new AnhNongSan().ten(DEFAULT_TEN);
        return anhNongSan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnhNongSan createUpdatedEntity(EntityManager em) {
        AnhNongSan anhNongSan = new AnhNongSan().ten(UPDATED_TEN);
        return anhNongSan;
    }

    @BeforeEach
    public void initTest() {
        anhNongSan = createEntity(em);
    }

    @Test
    @Transactional
    void createAnhNongSan() throws Exception {
        int databaseSizeBeforeCreate = anhNongSanRepository.findAll().size();
        // Create the AnhNongSan
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);
        restAnhNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO)))
            .andExpect(status().isCreated());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeCreate + 1);
        AnhNongSan testAnhNongSan = anhNongSanList.get(anhNongSanList.size() - 1);
        assertThat(testAnhNongSan.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void createAnhNongSanWithExistingId() throws Exception {
        // Create the AnhNongSan with an existing ID
        anhNongSan.setId(1L);
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        int databaseSizeBeforeCreate = anhNongSanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnhNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = anhNongSanRepository.findAll().size();
        // set the field null
        anhNongSan.setTen(null);

        // Create the AnhNongSan, which fails.
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        restAnhNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO)))
            .andExpect(status().isBadRequest());

        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnhNongSans() throws Exception {
        // Initialize the database
        anhNongSanRepository.saveAndFlush(anhNongSan);

        // Get all the anhNongSanList
        restAnhNongSanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anhNongSan.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)));
    }

    @Test
    @Transactional
    void getAnhNongSan() throws Exception {
        // Initialize the database
        anhNongSanRepository.saveAndFlush(anhNongSan);

        // Get the anhNongSan
        restAnhNongSanMockMvc
            .perform(get(ENTITY_API_URL_ID, anhNongSan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anhNongSan.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN));
    }

    @Test
    @Transactional
    void getNonExistingAnhNongSan() throws Exception {
        // Get the anhNongSan
        restAnhNongSanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnhNongSan() throws Exception {
        // Initialize the database
        anhNongSanRepository.saveAndFlush(anhNongSan);

        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();

        // Update the anhNongSan
        AnhNongSan updatedAnhNongSan = anhNongSanRepository.findById(anhNongSan.getId()).get();
        // Disconnect from session so that the updates on updatedAnhNongSan are not directly saved in db
        em.detach(updatedAnhNongSan);
        updatedAnhNongSan.ten(UPDATED_TEN);
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(updatedAnhNongSan);

        restAnhNongSanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anhNongSanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
        AnhNongSan testAnhNongSan = anhNongSanList.get(anhNongSanList.size() - 1);
        assertThat(testAnhNongSan.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void putNonExistingAnhNongSan() throws Exception {
        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();
        anhNongSan.setId(count.incrementAndGet());

        // Create the AnhNongSan
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnhNongSanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anhNongSanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnhNongSan() throws Exception {
        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();
        anhNongSan.setId(count.incrementAndGet());

        // Create the AnhNongSan
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhNongSanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnhNongSan() throws Exception {
        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();
        anhNongSan.setId(count.incrementAndGet());

        // Create the AnhNongSan
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhNongSanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnhNongSanWithPatch() throws Exception {
        // Initialize the database
        anhNongSanRepository.saveAndFlush(anhNongSan);

        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();

        // Update the anhNongSan using partial update
        AnhNongSan partialUpdatedAnhNongSan = new AnhNongSan();
        partialUpdatedAnhNongSan.setId(anhNongSan.getId());

        partialUpdatedAnhNongSan.ten(UPDATED_TEN);

        restAnhNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnhNongSan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnhNongSan))
            )
            .andExpect(status().isOk());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
        AnhNongSan testAnhNongSan = anhNongSanList.get(anhNongSanList.size() - 1);
        assertThat(testAnhNongSan.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void fullUpdateAnhNongSanWithPatch() throws Exception {
        // Initialize the database
        anhNongSanRepository.saveAndFlush(anhNongSan);

        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();

        // Update the anhNongSan using partial update
        AnhNongSan partialUpdatedAnhNongSan = new AnhNongSan();
        partialUpdatedAnhNongSan.setId(anhNongSan.getId());

        partialUpdatedAnhNongSan.ten(UPDATED_TEN);

        restAnhNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnhNongSan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnhNongSan))
            )
            .andExpect(status().isOk());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
        AnhNongSan testAnhNongSan = anhNongSanList.get(anhNongSanList.size() - 1);
        assertThat(testAnhNongSan.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void patchNonExistingAnhNongSan() throws Exception {
        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();
        anhNongSan.setId(count.incrementAndGet());

        // Create the AnhNongSan
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnhNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anhNongSanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnhNongSan() throws Exception {
        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();
        anhNongSan.setId(count.incrementAndGet());

        // Create the AnhNongSan
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnhNongSan() throws Exception {
        int databaseSizeBeforeUpdate = anhNongSanRepository.findAll().size();
        anhNongSan.setId(count.incrementAndGet());

        // Create the AnhNongSan
        AnhNongSanDTO anhNongSanDTO = anhNongSanMapper.toDto(anhNongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnhNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(anhNongSanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnhNongSan in the database
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnhNongSan() throws Exception {
        // Initialize the database
        anhNongSanRepository.saveAndFlush(anhNongSan);

        int databaseSizeBeforeDelete = anhNongSanRepository.findAll().size();

        // Delete the anhNongSan
        restAnhNongSanMockMvc
            .perform(delete(ENTITY_API_URL_ID, anhNongSan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnhNongSan> anhNongSanList = anhNongSanRepository.findAll();
        assertThat(anhNongSanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
