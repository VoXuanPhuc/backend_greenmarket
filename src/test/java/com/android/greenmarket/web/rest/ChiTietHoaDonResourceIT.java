package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.ChiTietHoaDon;
import com.android.greenmarket.repository.ChiTietHoaDonRepository;
import com.android.greenmarket.service.dto.ChiTietHoaDonDTO;
import com.android.greenmarket.service.mapper.ChiTietHoaDonMapper;
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
 * Integration tests for the {@link ChiTietHoaDonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChiTietHoaDonResourceIT {

    private static final Double DEFAULT_GIA = 1D;
    private static final Double UPDATED_GIA = 2D;

    private static final Integer DEFAULT_SOLUONG = 1;
    private static final Integer UPDATED_SOLUONG = 2;

    private static final String ENTITY_API_URL = "/api/chi-tiet-hoa-dons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChiTietHoaDonRepository chiTietHoaDonRepository;

    @Autowired
    private ChiTietHoaDonMapper chiTietHoaDonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChiTietHoaDonMockMvc;

    private ChiTietHoaDon chiTietHoaDon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietHoaDon createEntity(EntityManager em) {
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon().gia(DEFAULT_GIA).soluong(DEFAULT_SOLUONG);
        return chiTietHoaDon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietHoaDon createUpdatedEntity(EntityManager em) {
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon().gia(UPDATED_GIA).soluong(UPDATED_SOLUONG);
        return chiTietHoaDon;
    }

    @BeforeEach
    public void initTest() {
        chiTietHoaDon = createEntity(em);
    }

    @Test
    @Transactional
    void createChiTietHoaDon() throws Exception {
        int databaseSizeBeforeCreate = chiTietHoaDonRepository.findAll().size();
        // Create the ChiTietHoaDon
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);
        restChiTietHoaDonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeCreate + 1);
        ChiTietHoaDon testChiTietHoaDon = chiTietHoaDonList.get(chiTietHoaDonList.size() - 1);
        assertThat(testChiTietHoaDon.getGia()).isEqualTo(DEFAULT_GIA);
        assertThat(testChiTietHoaDon.getSoluong()).isEqualTo(DEFAULT_SOLUONG);
    }

    @Test
    @Transactional
    void createChiTietHoaDonWithExistingId() throws Exception {
        // Create the ChiTietHoaDon with an existing ID
        chiTietHoaDon.setId(1L);
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        int databaseSizeBeforeCreate = chiTietHoaDonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiTietHoaDonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietHoaDonRepository.findAll().size();
        // set the field null
        chiTietHoaDon.setGia(null);

        // Create the ChiTietHoaDon, which fails.
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        restChiTietHoaDonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoluongIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietHoaDonRepository.findAll().size();
        // set the field null
        chiTietHoaDon.setSoluong(null);

        // Create the ChiTietHoaDon, which fails.
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        restChiTietHoaDonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChiTietHoaDons() throws Exception {
        // Initialize the database
        chiTietHoaDonRepository.saveAndFlush(chiTietHoaDon);

        // Get all the chiTietHoaDonList
        restChiTietHoaDonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiTietHoaDon.getId().intValue())))
            .andExpect(jsonPath("$.[*].gia").value(hasItem(DEFAULT_GIA.doubleValue())))
            .andExpect(jsonPath("$.[*].soluong").value(hasItem(DEFAULT_SOLUONG)));
    }

    @Test
    @Transactional
    void getChiTietHoaDon() throws Exception {
        // Initialize the database
        chiTietHoaDonRepository.saveAndFlush(chiTietHoaDon);

        // Get the chiTietHoaDon
        restChiTietHoaDonMockMvc
            .perform(get(ENTITY_API_URL_ID, chiTietHoaDon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chiTietHoaDon.getId().intValue()))
            .andExpect(jsonPath("$.gia").value(DEFAULT_GIA.doubleValue()))
            .andExpect(jsonPath("$.soluong").value(DEFAULT_SOLUONG));
    }

    @Test
    @Transactional
    void getNonExistingChiTietHoaDon() throws Exception {
        // Get the chiTietHoaDon
        restChiTietHoaDonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChiTietHoaDon() throws Exception {
        // Initialize the database
        chiTietHoaDonRepository.saveAndFlush(chiTietHoaDon);

        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();

        // Update the chiTietHoaDon
        ChiTietHoaDon updatedChiTietHoaDon = chiTietHoaDonRepository.findById(chiTietHoaDon.getId()).get();
        // Disconnect from session so that the updates on updatedChiTietHoaDon are not directly saved in db
        em.detach(updatedChiTietHoaDon);
        updatedChiTietHoaDon.gia(UPDATED_GIA).soluong(UPDATED_SOLUONG);
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(updatedChiTietHoaDon);

        restChiTietHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietHoaDonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
        ChiTietHoaDon testChiTietHoaDon = chiTietHoaDonList.get(chiTietHoaDonList.size() - 1);
        assertThat(testChiTietHoaDon.getGia()).isEqualTo(UPDATED_GIA);
        assertThat(testChiTietHoaDon.getSoluong()).isEqualTo(UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    void putNonExistingChiTietHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();
        chiTietHoaDon.setId(count.incrementAndGet());

        // Create the ChiTietHoaDon
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietHoaDonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChiTietHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();
        chiTietHoaDon.setId(count.incrementAndGet());

        // Create the ChiTietHoaDon
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChiTietHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();
        chiTietHoaDon.setId(count.incrementAndGet());

        // Create the ChiTietHoaDon
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChiTietHoaDonWithPatch() throws Exception {
        // Initialize the database
        chiTietHoaDonRepository.saveAndFlush(chiTietHoaDon);

        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();

        // Update the chiTietHoaDon using partial update
        ChiTietHoaDon partialUpdatedChiTietHoaDon = new ChiTietHoaDon();
        partialUpdatedChiTietHoaDon.setId(chiTietHoaDon.getId());

        partialUpdatedChiTietHoaDon.soluong(UPDATED_SOLUONG);

        restChiTietHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietHoaDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietHoaDon))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
        ChiTietHoaDon testChiTietHoaDon = chiTietHoaDonList.get(chiTietHoaDonList.size() - 1);
        assertThat(testChiTietHoaDon.getGia()).isEqualTo(DEFAULT_GIA);
        assertThat(testChiTietHoaDon.getSoluong()).isEqualTo(UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    void fullUpdateChiTietHoaDonWithPatch() throws Exception {
        // Initialize the database
        chiTietHoaDonRepository.saveAndFlush(chiTietHoaDon);

        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();

        // Update the chiTietHoaDon using partial update
        ChiTietHoaDon partialUpdatedChiTietHoaDon = new ChiTietHoaDon();
        partialUpdatedChiTietHoaDon.setId(chiTietHoaDon.getId());

        partialUpdatedChiTietHoaDon.gia(UPDATED_GIA).soluong(UPDATED_SOLUONG);

        restChiTietHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietHoaDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietHoaDon))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
        ChiTietHoaDon testChiTietHoaDon = chiTietHoaDonList.get(chiTietHoaDonList.size() - 1);
        assertThat(testChiTietHoaDon.getGia()).isEqualTo(UPDATED_GIA);
        assertThat(testChiTietHoaDon.getSoluong()).isEqualTo(UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    void patchNonExistingChiTietHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();
        chiTietHoaDon.setId(count.incrementAndGet());

        // Create the ChiTietHoaDon
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chiTietHoaDonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChiTietHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();
        chiTietHoaDon.setId(count.incrementAndGet());

        // Create the ChiTietHoaDon
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChiTietHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = chiTietHoaDonRepository.findAll().size();
        chiTietHoaDon.setId(count.incrementAndGet());

        // Create the ChiTietHoaDon
        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonMapper.toDto(chiTietHoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietHoaDonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietHoaDon in the database
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChiTietHoaDon() throws Exception {
        // Initialize the database
        chiTietHoaDonRepository.saveAndFlush(chiTietHoaDon);

        int databaseSizeBeforeDelete = chiTietHoaDonRepository.findAll().size();

        // Delete the chiTietHoaDon
        restChiTietHoaDonMockMvc
            .perform(delete(ENTITY_API_URL_ID, chiTietHoaDon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAll();
        assertThat(chiTietHoaDonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
