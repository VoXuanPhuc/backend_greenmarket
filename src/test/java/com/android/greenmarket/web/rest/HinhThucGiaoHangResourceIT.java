package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.HinhThucGiaoHang;
import com.android.greenmarket.repository.HinhThucGiaoHangRepository;
import com.android.greenmarket.service.dto.HinhThucGiaoHangDTO;
import com.android.greenmarket.service.mapper.HinhThucGiaoHangMapper;
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
 * Integration tests for the {@link HinhThucGiaoHangResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HinhThucGiaoHangResourceIT {

    private static final String DEFAULT_MOTA = "AAAAAAAAAA";
    private static final String UPDATED_MOTA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hinh-thuc-giao-hangs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HinhThucGiaoHangRepository hinhThucGiaoHangRepository;

    @Autowired
    private HinhThucGiaoHangMapper hinhThucGiaoHangMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHinhThucGiaoHangMockMvc;

    private HinhThucGiaoHang hinhThucGiaoHang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HinhThucGiaoHang createEntity(EntityManager em) {
        HinhThucGiaoHang hinhThucGiaoHang = new HinhThucGiaoHang().mota(DEFAULT_MOTA);
        return hinhThucGiaoHang;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HinhThucGiaoHang createUpdatedEntity(EntityManager em) {
        HinhThucGiaoHang hinhThucGiaoHang = new HinhThucGiaoHang().mota(UPDATED_MOTA);
        return hinhThucGiaoHang;
    }

    @BeforeEach
    public void initTest() {
        hinhThucGiaoHang = createEntity(em);
    }

    @Test
    @Transactional
    void createHinhThucGiaoHang() throws Exception {
        int databaseSizeBeforeCreate = hinhThucGiaoHangRepository.findAll().size();
        // Create the HinhThucGiaoHang
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);
        restHinhThucGiaoHangMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeCreate + 1);
        HinhThucGiaoHang testHinhThucGiaoHang = hinhThucGiaoHangList.get(hinhThucGiaoHangList.size() - 1);
        assertThat(testHinhThucGiaoHang.getMota()).isEqualTo(DEFAULT_MOTA);
    }

    @Test
    @Transactional
    void createHinhThucGiaoHangWithExistingId() throws Exception {
        // Create the HinhThucGiaoHang with an existing ID
        hinhThucGiaoHang.setId(1L);
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        int databaseSizeBeforeCreate = hinhThucGiaoHangRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHinhThucGiaoHangMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = hinhThucGiaoHangRepository.findAll().size();
        // set the field null
        hinhThucGiaoHang.setMota(null);

        // Create the HinhThucGiaoHang, which fails.
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        restHinhThucGiaoHangMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isBadRequest());

        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHinhThucGiaoHangs() throws Exception {
        // Initialize the database
        hinhThucGiaoHangRepository.saveAndFlush(hinhThucGiaoHang);

        // Get all the hinhThucGiaoHangList
        restHinhThucGiaoHangMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hinhThucGiaoHang.getId().intValue())))
            .andExpect(jsonPath("$.[*].mota").value(hasItem(DEFAULT_MOTA)));
    }

    @Test
    @Transactional
    void getHinhThucGiaoHang() throws Exception {
        // Initialize the database
        hinhThucGiaoHangRepository.saveAndFlush(hinhThucGiaoHang);

        // Get the hinhThucGiaoHang
        restHinhThucGiaoHangMockMvc
            .perform(get(ENTITY_API_URL_ID, hinhThucGiaoHang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hinhThucGiaoHang.getId().intValue()))
            .andExpect(jsonPath("$.mota").value(DEFAULT_MOTA));
    }

    @Test
    @Transactional
    void getNonExistingHinhThucGiaoHang() throws Exception {
        // Get the hinhThucGiaoHang
        restHinhThucGiaoHangMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHinhThucGiaoHang() throws Exception {
        // Initialize the database
        hinhThucGiaoHangRepository.saveAndFlush(hinhThucGiaoHang);

        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();

        // Update the hinhThucGiaoHang
        HinhThucGiaoHang updatedHinhThucGiaoHang = hinhThucGiaoHangRepository.findById(hinhThucGiaoHang.getId()).get();
        // Disconnect from session so that the updates on updatedHinhThucGiaoHang are not directly saved in db
        em.detach(updatedHinhThucGiaoHang);
        updatedHinhThucGiaoHang.mota(UPDATED_MOTA);
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(updatedHinhThucGiaoHang);

        restHinhThucGiaoHangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hinhThucGiaoHangDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isOk());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
        HinhThucGiaoHang testHinhThucGiaoHang = hinhThucGiaoHangList.get(hinhThucGiaoHangList.size() - 1);
        assertThat(testHinhThucGiaoHang.getMota()).isEqualTo(UPDATED_MOTA);
    }

    @Test
    @Transactional
    void putNonExistingHinhThucGiaoHang() throws Exception {
        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();
        hinhThucGiaoHang.setId(count.incrementAndGet());

        // Create the HinhThucGiaoHang
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHinhThucGiaoHangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hinhThucGiaoHangDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHinhThucGiaoHang() throws Exception {
        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();
        hinhThucGiaoHang.setId(count.incrementAndGet());

        // Create the HinhThucGiaoHang
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHinhThucGiaoHangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHinhThucGiaoHang() throws Exception {
        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();
        hinhThucGiaoHang.setId(count.incrementAndGet());

        // Create the HinhThucGiaoHang
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHinhThucGiaoHangMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHinhThucGiaoHangWithPatch() throws Exception {
        // Initialize the database
        hinhThucGiaoHangRepository.saveAndFlush(hinhThucGiaoHang);

        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();

        // Update the hinhThucGiaoHang using partial update
        HinhThucGiaoHang partialUpdatedHinhThucGiaoHang = new HinhThucGiaoHang();
        partialUpdatedHinhThucGiaoHang.setId(hinhThucGiaoHang.getId());

        restHinhThucGiaoHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHinhThucGiaoHang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHinhThucGiaoHang))
            )
            .andExpect(status().isOk());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
        HinhThucGiaoHang testHinhThucGiaoHang = hinhThucGiaoHangList.get(hinhThucGiaoHangList.size() - 1);
        assertThat(testHinhThucGiaoHang.getMota()).isEqualTo(DEFAULT_MOTA);
    }

    @Test
    @Transactional
    void fullUpdateHinhThucGiaoHangWithPatch() throws Exception {
        // Initialize the database
        hinhThucGiaoHangRepository.saveAndFlush(hinhThucGiaoHang);

        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();

        // Update the hinhThucGiaoHang using partial update
        HinhThucGiaoHang partialUpdatedHinhThucGiaoHang = new HinhThucGiaoHang();
        partialUpdatedHinhThucGiaoHang.setId(hinhThucGiaoHang.getId());

        partialUpdatedHinhThucGiaoHang.mota(UPDATED_MOTA);

        restHinhThucGiaoHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHinhThucGiaoHang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHinhThucGiaoHang))
            )
            .andExpect(status().isOk());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
        HinhThucGiaoHang testHinhThucGiaoHang = hinhThucGiaoHangList.get(hinhThucGiaoHangList.size() - 1);
        assertThat(testHinhThucGiaoHang.getMota()).isEqualTo(UPDATED_MOTA);
    }

    @Test
    @Transactional
    void patchNonExistingHinhThucGiaoHang() throws Exception {
        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();
        hinhThucGiaoHang.setId(count.incrementAndGet());

        // Create the HinhThucGiaoHang
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHinhThucGiaoHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hinhThucGiaoHangDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHinhThucGiaoHang() throws Exception {
        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();
        hinhThucGiaoHang.setId(count.incrementAndGet());

        // Create the HinhThucGiaoHang
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHinhThucGiaoHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHinhThucGiaoHang() throws Exception {
        int databaseSizeBeforeUpdate = hinhThucGiaoHangRepository.findAll().size();
        hinhThucGiaoHang.setId(count.incrementAndGet());

        // Create the HinhThucGiaoHang
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHinhThucGiaoHangMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hinhThucGiaoHangDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HinhThucGiaoHang in the database
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHinhThucGiaoHang() throws Exception {
        // Initialize the database
        hinhThucGiaoHangRepository.saveAndFlush(hinhThucGiaoHang);

        int databaseSizeBeforeDelete = hinhThucGiaoHangRepository.findAll().size();

        // Delete the hinhThucGiaoHang
        restHinhThucGiaoHangMockMvc
            .perform(delete(ENTITY_API_URL_ID, hinhThucGiaoHang.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HinhThucGiaoHang> hinhThucGiaoHangList = hinhThucGiaoHangRepository.findAll();
        assertThat(hinhThucGiaoHangList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
