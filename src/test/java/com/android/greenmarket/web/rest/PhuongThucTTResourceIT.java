package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.PhuongThucTT;
import com.android.greenmarket.repository.PhuongThucTTRepository;
import com.android.greenmarket.service.dto.PhuongThucTTDTO;
import com.android.greenmarket.service.mapper.PhuongThucTTMapper;
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
 * Integration tests for the {@link PhuongThucTTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhuongThucTTResourceIT {

    private static final String DEFAULT_TEN_PTTT = "AAAAAAAAAA";
    private static final String UPDATED_TEN_PTTT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phuong-thuc-tts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhuongThucTTRepository phuongThucTTRepository;

    @Autowired
    private PhuongThucTTMapper phuongThucTTMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhuongThucTTMockMvc;

    private PhuongThucTT phuongThucTT;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhuongThucTT createEntity(EntityManager em) {
        PhuongThucTT phuongThucTT = new PhuongThucTT().tenPTTT(DEFAULT_TEN_PTTT);
        return phuongThucTT;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhuongThucTT createUpdatedEntity(EntityManager em) {
        PhuongThucTT phuongThucTT = new PhuongThucTT().tenPTTT(UPDATED_TEN_PTTT);
        return phuongThucTT;
    }

    @BeforeEach
    public void initTest() {
        phuongThucTT = createEntity(em);
    }

    @Test
    @Transactional
    void createPhuongThucTT() throws Exception {
        int databaseSizeBeforeCreate = phuongThucTTRepository.findAll().size();
        // Create the PhuongThucTT
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);
        restPhuongThucTTMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeCreate + 1);
        PhuongThucTT testPhuongThucTT = phuongThucTTList.get(phuongThucTTList.size() - 1);
        assertThat(testPhuongThucTT.getTenPTTT()).isEqualTo(DEFAULT_TEN_PTTT);
    }

    @Test
    @Transactional
    void createPhuongThucTTWithExistingId() throws Exception {
        // Create the PhuongThucTT with an existing ID
        phuongThucTT.setId(1L);
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        int databaseSizeBeforeCreate = phuongThucTTRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhuongThucTTMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenPTTTIsRequired() throws Exception {
        int databaseSizeBeforeTest = phuongThucTTRepository.findAll().size();
        // set the field null
        phuongThucTT.setTenPTTT(null);

        // Create the PhuongThucTT, which fails.
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        restPhuongThucTTMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isBadRequest());

        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhuongThucTTS() throws Exception {
        // Initialize the database
        phuongThucTTRepository.saveAndFlush(phuongThucTT);

        // Get all the phuongThucTTList
        restPhuongThucTTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phuongThucTT.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenPTTT").value(hasItem(DEFAULT_TEN_PTTT)));
    }

    @Test
    @Transactional
    void getPhuongThucTT() throws Exception {
        // Initialize the database
        phuongThucTTRepository.saveAndFlush(phuongThucTT);

        // Get the phuongThucTT
        restPhuongThucTTMockMvc
            .perform(get(ENTITY_API_URL_ID, phuongThucTT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phuongThucTT.getId().intValue()))
            .andExpect(jsonPath("$.tenPTTT").value(DEFAULT_TEN_PTTT));
    }

    @Test
    @Transactional
    void getNonExistingPhuongThucTT() throws Exception {
        // Get the phuongThucTT
        restPhuongThucTTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhuongThucTT() throws Exception {
        // Initialize the database
        phuongThucTTRepository.saveAndFlush(phuongThucTT);

        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();

        // Update the phuongThucTT
        PhuongThucTT updatedPhuongThucTT = phuongThucTTRepository.findById(phuongThucTT.getId()).get();
        // Disconnect from session so that the updates on updatedPhuongThucTT are not directly saved in db
        em.detach(updatedPhuongThucTT);
        updatedPhuongThucTT.tenPTTT(UPDATED_TEN_PTTT);
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(updatedPhuongThucTT);

        restPhuongThucTTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phuongThucTTDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
        PhuongThucTT testPhuongThucTT = phuongThucTTList.get(phuongThucTTList.size() - 1);
        assertThat(testPhuongThucTT.getTenPTTT()).isEqualTo(UPDATED_TEN_PTTT);
    }

    @Test
    @Transactional
    void putNonExistingPhuongThucTT() throws Exception {
        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();
        phuongThucTT.setId(count.incrementAndGet());

        // Create the PhuongThucTT
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhuongThucTTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phuongThucTTDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhuongThucTT() throws Exception {
        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();
        phuongThucTT.setId(count.incrementAndGet());

        // Create the PhuongThucTT
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuongThucTTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhuongThucTT() throws Exception {
        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();
        phuongThucTT.setId(count.incrementAndGet());

        // Create the PhuongThucTT
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuongThucTTMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhuongThucTTWithPatch() throws Exception {
        // Initialize the database
        phuongThucTTRepository.saveAndFlush(phuongThucTT);

        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();

        // Update the phuongThucTT using partial update
        PhuongThucTT partialUpdatedPhuongThucTT = new PhuongThucTT();
        partialUpdatedPhuongThucTT.setId(phuongThucTT.getId());

        partialUpdatedPhuongThucTT.tenPTTT(UPDATED_TEN_PTTT);

        restPhuongThucTTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhuongThucTT.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhuongThucTT))
            )
            .andExpect(status().isOk());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
        PhuongThucTT testPhuongThucTT = phuongThucTTList.get(phuongThucTTList.size() - 1);
        assertThat(testPhuongThucTT.getTenPTTT()).isEqualTo(UPDATED_TEN_PTTT);
    }

    @Test
    @Transactional
    void fullUpdatePhuongThucTTWithPatch() throws Exception {
        // Initialize the database
        phuongThucTTRepository.saveAndFlush(phuongThucTT);

        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();

        // Update the phuongThucTT using partial update
        PhuongThucTT partialUpdatedPhuongThucTT = new PhuongThucTT();
        partialUpdatedPhuongThucTT.setId(phuongThucTT.getId());

        partialUpdatedPhuongThucTT.tenPTTT(UPDATED_TEN_PTTT);

        restPhuongThucTTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhuongThucTT.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhuongThucTT))
            )
            .andExpect(status().isOk());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
        PhuongThucTT testPhuongThucTT = phuongThucTTList.get(phuongThucTTList.size() - 1);
        assertThat(testPhuongThucTT.getTenPTTT()).isEqualTo(UPDATED_TEN_PTTT);
    }

    @Test
    @Transactional
    void patchNonExistingPhuongThucTT() throws Exception {
        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();
        phuongThucTT.setId(count.incrementAndGet());

        // Create the PhuongThucTT
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhuongThucTTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phuongThucTTDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhuongThucTT() throws Exception {
        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();
        phuongThucTT.setId(count.incrementAndGet());

        // Create the PhuongThucTT
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuongThucTTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhuongThucTT() throws Exception {
        int databaseSizeBeforeUpdate = phuongThucTTRepository.findAll().size();
        phuongThucTT.setId(count.incrementAndGet());

        // Create the PhuongThucTT
        PhuongThucTTDTO phuongThucTTDTO = phuongThucTTMapper.toDto(phuongThucTT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuongThucTTMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phuongThucTTDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhuongThucTT in the database
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhuongThucTT() throws Exception {
        // Initialize the database
        phuongThucTTRepository.saveAndFlush(phuongThucTT);

        int databaseSizeBeforeDelete = phuongThucTTRepository.findAll().size();

        // Delete the phuongThucTT
        restPhuongThucTTMockMvc
            .perform(delete(ENTITY_API_URL_ID, phuongThucTT.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhuongThucTT> phuongThucTTList = phuongThucTTRepository.findAll();
        assertThat(phuongThucTTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
