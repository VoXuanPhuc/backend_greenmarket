package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.HoaDon;
import com.android.greenmarket.repository.HoaDonRepository;
import com.android.greenmarket.service.dto.HoaDonDTO;
import com.android.greenmarket.service.mapper.HoaDonMapper;
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
 * Integration tests for the {@link HoaDonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoaDonResourceIT {

    private static final Double DEFAULT_TONGTHANHTOAN = 1D;
    private static final Double UPDATED_TONGTHANHTOAN = 2D;

    private static final Double DEFAULT_CHIPHIVANCHUYEN = 1D;
    private static final Double UPDATED_CHIPHIVANCHUYEN = 2D;

    private static final Instant DEFAULT_NGAYTHANHTOAN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAYTHANHTOAN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NGAYTAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAYTAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TRANGTHAI = "AAAAAAAAAA";
    private static final String UPDATED_TRANGTHAI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hoa-dons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonMapper hoaDonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoaDonMockMvc;

    private HoaDon hoaDon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoaDon createEntity(EntityManager em) {
        HoaDon hoaDon = new HoaDon()
            .tongthanhtoan(DEFAULT_TONGTHANHTOAN)
            .chiphivanchuyen(DEFAULT_CHIPHIVANCHUYEN)
            .ngaythanhtoan(DEFAULT_NGAYTHANHTOAN)
            .ngaytao(DEFAULT_NGAYTAO)
            .trangthai(DEFAULT_TRANGTHAI);
        return hoaDon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoaDon createUpdatedEntity(EntityManager em) {
        HoaDon hoaDon = new HoaDon()
            .tongthanhtoan(UPDATED_TONGTHANHTOAN)
            .chiphivanchuyen(UPDATED_CHIPHIVANCHUYEN)
            .ngaythanhtoan(UPDATED_NGAYTHANHTOAN)
            .ngaytao(UPDATED_NGAYTAO)
            .trangthai(UPDATED_TRANGTHAI);
        return hoaDon;
    }

    @BeforeEach
    public void initTest() {
        hoaDon = createEntity(em);
    }

    @Test
    @Transactional
    void createHoaDon() throws Exception {
        int databaseSizeBeforeCreate = hoaDonRepository.findAll().size();
        // Create the HoaDon
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);
        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDonDTO)))
            .andExpect(status().isCreated());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeCreate + 1);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTongthanhtoan()).isEqualTo(DEFAULT_TONGTHANHTOAN);
        assertThat(testHoaDon.getChiphivanchuyen()).isEqualTo(DEFAULT_CHIPHIVANCHUYEN);
        assertThat(testHoaDon.getNgaythanhtoan()).isEqualTo(DEFAULT_NGAYTHANHTOAN);
        assertThat(testHoaDon.getNgaytao()).isEqualTo(DEFAULT_NGAYTAO);
        assertThat(testHoaDon.getTrangthai()).isEqualTo(DEFAULT_TRANGTHAI);
    }

    @Test
    @Transactional
    void createHoaDonWithExistingId() throws Exception {
        // Create the HoaDon with an existing ID
        hoaDon.setId(1L);
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        int databaseSizeBeforeCreate = hoaDonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTongthanhtoanIsRequired() throws Exception {
        int databaseSizeBeforeTest = hoaDonRepository.findAll().size();
        // set the field null
        hoaDon.setTongthanhtoan(null);

        // Create the HoaDon, which fails.
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDonDTO)))
            .andExpect(status().isBadRequest());

        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChiphivanchuyenIsRequired() throws Exception {
        int databaseSizeBeforeTest = hoaDonRepository.findAll().size();
        // set the field null
        hoaDon.setChiphivanchuyen(null);

        // Create the HoaDon, which fails.
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDonDTO)))
            .andExpect(status().isBadRequest());

        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgaythanhtoanIsRequired() throws Exception {
        int databaseSizeBeforeTest = hoaDonRepository.findAll().size();
        // set the field null
        hoaDon.setNgaythanhtoan(null);

        // Create the HoaDon, which fails.
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDonDTO)))
            .andExpect(status().isBadRequest());

        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgaytaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = hoaDonRepository.findAll().size();
        // set the field null
        hoaDon.setNgaytao(null);

        // Create the HoaDon, which fails.
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDonDTO)))
            .andExpect(status().isBadRequest());

        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHoaDons() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        // Get all the hoaDonList
        restHoaDonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoaDon.getId().intValue())))
            .andExpect(jsonPath("$.[*].tongthanhtoan").value(hasItem(DEFAULT_TONGTHANHTOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].chiphivanchuyen").value(hasItem(DEFAULT_CHIPHIVANCHUYEN.doubleValue())))
            .andExpect(jsonPath("$.[*].ngaythanhtoan").value(hasItem(DEFAULT_NGAYTHANHTOAN.toString())))
            .andExpect(jsonPath("$.[*].ngaytao").value(hasItem(DEFAULT_NGAYTAO.toString())))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI)));
    }

    @Test
    @Transactional
    void getHoaDon() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        // Get the hoaDon
        restHoaDonMockMvc
            .perform(get(ENTITY_API_URL_ID, hoaDon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hoaDon.getId().intValue()))
            .andExpect(jsonPath("$.tongthanhtoan").value(DEFAULT_TONGTHANHTOAN.doubleValue()))
            .andExpect(jsonPath("$.chiphivanchuyen").value(DEFAULT_CHIPHIVANCHUYEN.doubleValue()))
            .andExpect(jsonPath("$.ngaythanhtoan").value(DEFAULT_NGAYTHANHTOAN.toString()))
            .andExpect(jsonPath("$.ngaytao").value(DEFAULT_NGAYTAO.toString()))
            .andExpect(jsonPath("$.trangthai").value(DEFAULT_TRANGTHAI));
    }

    @Test
    @Transactional
    void getNonExistingHoaDon() throws Exception {
        // Get the hoaDon
        restHoaDonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHoaDon() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();

        // Update the hoaDon
        HoaDon updatedHoaDon = hoaDonRepository.findById(hoaDon.getId()).get();
        // Disconnect from session so that the updates on updatedHoaDon are not directly saved in db
        em.detach(updatedHoaDon);
        updatedHoaDon
            .tongthanhtoan(UPDATED_TONGTHANHTOAN)
            .chiphivanchuyen(UPDATED_CHIPHIVANCHUYEN)
            .ngaythanhtoan(UPDATED_NGAYTHANHTOAN)
            .ngaytao(UPDATED_NGAYTAO)
            .trangthai(UPDATED_TRANGTHAI);
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(updatedHoaDon);

        restHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoaDonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoaDonDTO))
            )
            .andExpect(status().isOk());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTongthanhtoan()).isEqualTo(UPDATED_TONGTHANHTOAN);
        assertThat(testHoaDon.getChiphivanchuyen()).isEqualTo(UPDATED_CHIPHIVANCHUYEN);
        assertThat(testHoaDon.getNgaythanhtoan()).isEqualTo(UPDATED_NGAYTHANHTOAN);
        assertThat(testHoaDon.getNgaytao()).isEqualTo(UPDATED_NGAYTAO);
        assertThat(testHoaDon.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    void putNonExistingHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // Create the HoaDon
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoaDonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // Create the HoaDon
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // Create the HoaDon
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoaDonWithPatch() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();

        // Update the hoaDon using partial update
        HoaDon partialUpdatedHoaDon = new HoaDon();
        partialUpdatedHoaDon.setId(hoaDon.getId());

        partialUpdatedHoaDon.chiphivanchuyen(UPDATED_CHIPHIVANCHUYEN).ngaythanhtoan(UPDATED_NGAYTHANHTOAN).trangthai(UPDATED_TRANGTHAI);

        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoaDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoaDon))
            )
            .andExpect(status().isOk());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTongthanhtoan()).isEqualTo(DEFAULT_TONGTHANHTOAN);
        assertThat(testHoaDon.getChiphivanchuyen()).isEqualTo(UPDATED_CHIPHIVANCHUYEN);
        assertThat(testHoaDon.getNgaythanhtoan()).isEqualTo(UPDATED_NGAYTHANHTOAN);
        assertThat(testHoaDon.getNgaytao()).isEqualTo(DEFAULT_NGAYTAO);
        assertThat(testHoaDon.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    void fullUpdateHoaDonWithPatch() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();

        // Update the hoaDon using partial update
        HoaDon partialUpdatedHoaDon = new HoaDon();
        partialUpdatedHoaDon.setId(hoaDon.getId());

        partialUpdatedHoaDon
            .tongthanhtoan(UPDATED_TONGTHANHTOAN)
            .chiphivanchuyen(UPDATED_CHIPHIVANCHUYEN)
            .ngaythanhtoan(UPDATED_NGAYTHANHTOAN)
            .ngaytao(UPDATED_NGAYTAO)
            .trangthai(UPDATED_TRANGTHAI);

        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoaDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoaDon))
            )
            .andExpect(status().isOk());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTongthanhtoan()).isEqualTo(UPDATED_TONGTHANHTOAN);
        assertThat(testHoaDon.getChiphivanchuyen()).isEqualTo(UPDATED_CHIPHIVANCHUYEN);
        assertThat(testHoaDon.getNgaythanhtoan()).isEqualTo(UPDATED_NGAYTHANHTOAN);
        assertThat(testHoaDon.getNgaytao()).isEqualTo(UPDATED_NGAYTAO);
        assertThat(testHoaDon.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    void patchNonExistingHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // Create the HoaDon
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hoaDonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // Create the HoaDon
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoaDonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // Create the HoaDon
        HoaDonDTO hoaDonDTO = hoaDonMapper.toDto(hoaDon);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hoaDonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoaDon() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeDelete = hoaDonRepository.findAll().size();

        // Delete the hoaDon
        restHoaDonMockMvc
            .perform(delete(ENTITY_API_URL_ID, hoaDon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
