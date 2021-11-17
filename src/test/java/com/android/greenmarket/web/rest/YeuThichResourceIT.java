package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.YeuThich;
import com.android.greenmarket.repository.YeuThichRepository;
import com.android.greenmarket.service.dto.YeuThichDTO;
import com.android.greenmarket.service.mapper.YeuThichMapper;
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
 * Integration tests for the {@link YeuThichResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class YeuThichResourceIT {

    private static final String ENTITY_API_URL = "/api/yeu-thiches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private YeuThichRepository yeuThichRepository;

    @Autowired
    private YeuThichMapper yeuThichMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restYeuThichMockMvc;

    private YeuThich yeuThich;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YeuThich createEntity(EntityManager em) {
        YeuThich yeuThich = new YeuThich();
        return yeuThich;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YeuThich createUpdatedEntity(EntityManager em) {
        YeuThich yeuThich = new YeuThich();
        return yeuThich;
    }

    @BeforeEach
    public void initTest() {
        yeuThich = createEntity(em);
    }

    @Test
    @Transactional
    void createYeuThich() throws Exception {
        int databaseSizeBeforeCreate = yeuThichRepository.findAll().size();
        // Create the YeuThich
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);
        restYeuThichMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yeuThichDTO)))
            .andExpect(status().isCreated());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeCreate + 1);
        YeuThich testYeuThich = yeuThichList.get(yeuThichList.size() - 1);
    }

    @Test
    @Transactional
    void createYeuThichWithExistingId() throws Exception {
        // Create the YeuThich with an existing ID
        yeuThich.setId(1L);
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);

        int databaseSizeBeforeCreate = yeuThichRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restYeuThichMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yeuThichDTO)))
            .andExpect(status().isBadRequest());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllYeuThiches() throws Exception {
        // Initialize the database
        yeuThichRepository.saveAndFlush(yeuThich);

        // Get all the yeuThichList
        restYeuThichMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(yeuThich.getId().intValue())));
    }

    @Test
    @Transactional
    void getYeuThich() throws Exception {
        // Initialize the database
        yeuThichRepository.saveAndFlush(yeuThich);

        // Get the yeuThich
        restYeuThichMockMvc
            .perform(get(ENTITY_API_URL_ID, yeuThich.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(yeuThich.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingYeuThich() throws Exception {
        // Get the yeuThich
        restYeuThichMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewYeuThich() throws Exception {
        // Initialize the database
        yeuThichRepository.saveAndFlush(yeuThich);

        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();

        // Update the yeuThich
        YeuThich updatedYeuThich = yeuThichRepository.findById(yeuThich.getId()).get();
        // Disconnect from session so that the updates on updatedYeuThich are not directly saved in db
        em.detach(updatedYeuThich);
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(updatedYeuThich);

        restYeuThichMockMvc
            .perform(
                put(ENTITY_API_URL_ID, yeuThichDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(yeuThichDTO))
            )
            .andExpect(status().isOk());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
        YeuThich testYeuThich = yeuThichList.get(yeuThichList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingYeuThich() throws Exception {
        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();
        yeuThich.setId(count.incrementAndGet());

        // Create the YeuThich
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYeuThichMockMvc
            .perform(
                put(ENTITY_API_URL_ID, yeuThichDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(yeuThichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchYeuThich() throws Exception {
        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();
        yeuThich.setId(count.incrementAndGet());

        // Create the YeuThich
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYeuThichMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(yeuThichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamYeuThich() throws Exception {
        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();
        yeuThich.setId(count.incrementAndGet());

        // Create the YeuThich
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYeuThichMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yeuThichDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateYeuThichWithPatch() throws Exception {
        // Initialize the database
        yeuThichRepository.saveAndFlush(yeuThich);

        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();

        // Update the yeuThich using partial update
        YeuThich partialUpdatedYeuThich = new YeuThich();
        partialUpdatedYeuThich.setId(yeuThich.getId());

        restYeuThichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedYeuThich.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedYeuThich))
            )
            .andExpect(status().isOk());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
        YeuThich testYeuThich = yeuThichList.get(yeuThichList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateYeuThichWithPatch() throws Exception {
        // Initialize the database
        yeuThichRepository.saveAndFlush(yeuThich);

        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();

        // Update the yeuThich using partial update
        YeuThich partialUpdatedYeuThich = new YeuThich();
        partialUpdatedYeuThich.setId(yeuThich.getId());

        restYeuThichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedYeuThich.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedYeuThich))
            )
            .andExpect(status().isOk());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
        YeuThich testYeuThich = yeuThichList.get(yeuThichList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingYeuThich() throws Exception {
        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();
        yeuThich.setId(count.incrementAndGet());

        // Create the YeuThich
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYeuThichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, yeuThichDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(yeuThichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchYeuThich() throws Exception {
        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();
        yeuThich.setId(count.incrementAndGet());

        // Create the YeuThich
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYeuThichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(yeuThichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamYeuThich() throws Exception {
        int databaseSizeBeforeUpdate = yeuThichRepository.findAll().size();
        yeuThich.setId(count.incrementAndGet());

        // Create the YeuThich
        YeuThichDTO yeuThichDTO = yeuThichMapper.toDto(yeuThich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYeuThichMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(yeuThichDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the YeuThich in the database
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteYeuThich() throws Exception {
        // Initialize the database
        yeuThichRepository.saveAndFlush(yeuThich);

        int databaseSizeBeforeDelete = yeuThichRepository.findAll().size();

        // Delete the yeuThich
        restYeuThichMockMvc
            .perform(delete(ENTITY_API_URL_ID, yeuThich.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<YeuThich> yeuThichList = yeuThichRepository.findAll();
        assertThat(yeuThichList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
