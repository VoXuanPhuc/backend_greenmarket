package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.NongSan;
import com.android.greenmarket.repository.NongSanRepository;
import com.android.greenmarket.service.dto.NongSanDTO;
import com.android.greenmarket.service.mapper.NongSanMapper;
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
 * Integration tests for the {@link NongSanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NongSanResourceIT {

    private static final String DEFAULT_TEN_NS = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NS = "BBBBBBBBBB";

    private static final Double DEFAULT_GIA = 1D;
    private static final Double UPDATED_GIA = 2D;

    private static final Integer DEFAULT_SOLUONG_NHAP = 1;
    private static final Integer UPDATED_SOLUONG_NHAP = 2;

    private static final Integer DEFAULT_SOLUONG_CON = 1;
    private static final Integer UPDATED_SOLUONG_CON = 2;

    private static final Instant DEFAULT_NOI_SAN_XUAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOI_SAN_XUAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MO_TA_NS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MO_TA_NS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nong-sans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NongSanRepository nongSanRepository;

    @Autowired
    private NongSanMapper nongSanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNongSanMockMvc;

    private NongSan nongSan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NongSan createEntity(EntityManager em) {
        NongSan nongSan = new NongSan()
            .tenNS(DEFAULT_TEN_NS)
            .gia(DEFAULT_GIA)
            .soluongNhap(DEFAULT_SOLUONG_NHAP)
            .soluongCon(DEFAULT_SOLUONG_CON)
            .noiSanXuat(DEFAULT_NOI_SAN_XUAT)
            .moTaNS(DEFAULT_MO_TA_NS);
        return nongSan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NongSan createUpdatedEntity(EntityManager em) {
        NongSan nongSan = new NongSan()
            .tenNS(UPDATED_TEN_NS)
            .gia(UPDATED_GIA)
            .soluongNhap(UPDATED_SOLUONG_NHAP)
            .soluongCon(UPDATED_SOLUONG_CON)
            .noiSanXuat(UPDATED_NOI_SAN_XUAT)
            .moTaNS(UPDATED_MO_TA_NS);
        return nongSan;
    }

    @BeforeEach
    public void initTest() {
        nongSan = createEntity(em);
    }

    @Test
    @Transactional
    void createNongSan() throws Exception {
        int databaseSizeBeforeCreate = nongSanRepository.findAll().size();
        // Create the NongSan
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);
        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isCreated());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeCreate + 1);
        NongSan testNongSan = nongSanList.get(nongSanList.size() - 1);
        assertThat(testNongSan.getTenNS()).isEqualTo(DEFAULT_TEN_NS);
        assertThat(testNongSan.getGia()).isEqualTo(DEFAULT_GIA);
        assertThat(testNongSan.getSoluongNhap()).isEqualTo(DEFAULT_SOLUONG_NHAP);
        assertThat(testNongSan.getSoluongCon()).isEqualTo(DEFAULT_SOLUONG_CON);
        assertThat(testNongSan.getNoiSanXuat()).isEqualTo(DEFAULT_NOI_SAN_XUAT);
        assertThat(testNongSan.getMoTaNS()).isEqualTo(DEFAULT_MO_TA_NS);
    }

    @Test
    @Transactional
    void createNongSanWithExistingId() throws Exception {
        // Create the NongSan with an existing ID
        nongSan.setId(1L);
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        int databaseSizeBeforeCreate = nongSanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenNSIsRequired() throws Exception {
        int databaseSizeBeforeTest = nongSanRepository.findAll().size();
        // set the field null
        nongSan.setTenNS(null);

        // Create the NongSan, which fails.
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isBadRequest());

        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = nongSanRepository.findAll().size();
        // set the field null
        nongSan.setGia(null);

        // Create the NongSan, which fails.
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isBadRequest());

        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoluongNhapIsRequired() throws Exception {
        int databaseSizeBeforeTest = nongSanRepository.findAll().size();
        // set the field null
        nongSan.setSoluongNhap(null);

        // Create the NongSan, which fails.
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isBadRequest());

        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoluongConIsRequired() throws Exception {
        int databaseSizeBeforeTest = nongSanRepository.findAll().size();
        // set the field null
        nongSan.setSoluongCon(null);

        // Create the NongSan, which fails.
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isBadRequest());

        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNoiSanXuatIsRequired() throws Exception {
        int databaseSizeBeforeTest = nongSanRepository.findAll().size();
        // set the field null
        nongSan.setNoiSanXuat(null);

        // Create the NongSan, which fails.
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isBadRequest());

        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoTaNSIsRequired() throws Exception {
        int databaseSizeBeforeTest = nongSanRepository.findAll().size();
        // set the field null
        nongSan.setMoTaNS(null);

        // Create the NongSan, which fails.
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        restNongSanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isBadRequest());

        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNongSans() throws Exception {
        // Initialize the database
        nongSanRepository.saveAndFlush(nongSan);

        // Get all the nongSanList
        restNongSanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nongSan.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenNS").value(hasItem(DEFAULT_TEN_NS)))
            .andExpect(jsonPath("$.[*].gia").value(hasItem(DEFAULT_GIA.doubleValue())))
            .andExpect(jsonPath("$.[*].soluongNhap").value(hasItem(DEFAULT_SOLUONG_NHAP)))
            .andExpect(jsonPath("$.[*].soluongCon").value(hasItem(DEFAULT_SOLUONG_CON)))
            .andExpect(jsonPath("$.[*].noiSanXuat").value(hasItem(DEFAULT_NOI_SAN_XUAT.toString())))
            .andExpect(jsonPath("$.[*].moTaNS").value(hasItem(DEFAULT_MO_TA_NS.toString())));
    }

    @Test
    @Transactional
    void getNongSan() throws Exception {
        // Initialize the database
        nongSanRepository.saveAndFlush(nongSan);

        // Get the nongSan
        restNongSanMockMvc
            .perform(get(ENTITY_API_URL_ID, nongSan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nongSan.getId().intValue()))
            .andExpect(jsonPath("$.tenNS").value(DEFAULT_TEN_NS))
            .andExpect(jsonPath("$.gia").value(DEFAULT_GIA.doubleValue()))
            .andExpect(jsonPath("$.soluongNhap").value(DEFAULT_SOLUONG_NHAP))
            .andExpect(jsonPath("$.soluongCon").value(DEFAULT_SOLUONG_CON))
            .andExpect(jsonPath("$.noiSanXuat").value(DEFAULT_NOI_SAN_XUAT.toString()))
            .andExpect(jsonPath("$.moTaNS").value(DEFAULT_MO_TA_NS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNongSan() throws Exception {
        // Get the nongSan
        restNongSanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNongSan() throws Exception {
        // Initialize the database
        nongSanRepository.saveAndFlush(nongSan);

        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();

        // Update the nongSan
        NongSan updatedNongSan = nongSanRepository.findById(nongSan.getId()).get();
        // Disconnect from session so that the updates on updatedNongSan are not directly saved in db
        em.detach(updatedNongSan);
        updatedNongSan
            .tenNS(UPDATED_TEN_NS)
            .gia(UPDATED_GIA)
            .soluongNhap(UPDATED_SOLUONG_NHAP)
            .soluongCon(UPDATED_SOLUONG_CON)
            .noiSanXuat(UPDATED_NOI_SAN_XUAT)
            .moTaNS(UPDATED_MO_TA_NS);
        NongSanDTO nongSanDTO = nongSanMapper.toDto(updatedNongSan);

        restNongSanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nongSanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nongSanDTO))
            )
            .andExpect(status().isOk());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
        NongSan testNongSan = nongSanList.get(nongSanList.size() - 1);
        assertThat(testNongSan.getTenNS()).isEqualTo(UPDATED_TEN_NS);
        assertThat(testNongSan.getGia()).isEqualTo(UPDATED_GIA);
        assertThat(testNongSan.getSoluongNhap()).isEqualTo(UPDATED_SOLUONG_NHAP);
        assertThat(testNongSan.getSoluongCon()).isEqualTo(UPDATED_SOLUONG_CON);
        assertThat(testNongSan.getNoiSanXuat()).isEqualTo(UPDATED_NOI_SAN_XUAT);
        assertThat(testNongSan.getMoTaNS()).isEqualTo(UPDATED_MO_TA_NS);
    }

    @Test
    @Transactional
    void putNonExistingNongSan() throws Exception {
        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();
        nongSan.setId(count.incrementAndGet());

        // Create the NongSan
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNongSanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nongSanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNongSan() throws Exception {
        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();
        nongSan.setId(count.incrementAndGet());

        // Create the NongSan
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNongSanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNongSan() throws Exception {
        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();
        nongSan.setId(count.incrementAndGet());

        // Create the NongSan
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNongSanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nongSanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNongSanWithPatch() throws Exception {
        // Initialize the database
        nongSanRepository.saveAndFlush(nongSan);

        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();

        // Update the nongSan using partial update
        NongSan partialUpdatedNongSan = new NongSan();
        partialUpdatedNongSan.setId(nongSan.getId());

        partialUpdatedNongSan.noiSanXuat(UPDATED_NOI_SAN_XUAT).moTaNS(UPDATED_MO_TA_NS);

        restNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNongSan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNongSan))
            )
            .andExpect(status().isOk());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
        NongSan testNongSan = nongSanList.get(nongSanList.size() - 1);
        assertThat(testNongSan.getTenNS()).isEqualTo(DEFAULT_TEN_NS);
        assertThat(testNongSan.getGia()).isEqualTo(DEFAULT_GIA);
        assertThat(testNongSan.getSoluongNhap()).isEqualTo(DEFAULT_SOLUONG_NHAP);
        assertThat(testNongSan.getSoluongCon()).isEqualTo(DEFAULT_SOLUONG_CON);
        assertThat(testNongSan.getNoiSanXuat()).isEqualTo(UPDATED_NOI_SAN_XUAT);
        assertThat(testNongSan.getMoTaNS()).isEqualTo(UPDATED_MO_TA_NS);
    }

    @Test
    @Transactional
    void fullUpdateNongSanWithPatch() throws Exception {
        // Initialize the database
        nongSanRepository.saveAndFlush(nongSan);

        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();

        // Update the nongSan using partial update
        NongSan partialUpdatedNongSan = new NongSan();
        partialUpdatedNongSan.setId(nongSan.getId());

        partialUpdatedNongSan
            .tenNS(UPDATED_TEN_NS)
            .gia(UPDATED_GIA)
            .soluongNhap(UPDATED_SOLUONG_NHAP)
            .soluongCon(UPDATED_SOLUONG_CON)
            .noiSanXuat(UPDATED_NOI_SAN_XUAT)
            .moTaNS(UPDATED_MO_TA_NS);

        restNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNongSan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNongSan))
            )
            .andExpect(status().isOk());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
        NongSan testNongSan = nongSanList.get(nongSanList.size() - 1);
        assertThat(testNongSan.getTenNS()).isEqualTo(UPDATED_TEN_NS);
        assertThat(testNongSan.getGia()).isEqualTo(UPDATED_GIA);
        assertThat(testNongSan.getSoluongNhap()).isEqualTo(UPDATED_SOLUONG_NHAP);
        assertThat(testNongSan.getSoluongCon()).isEqualTo(UPDATED_SOLUONG_CON);
        assertThat(testNongSan.getNoiSanXuat()).isEqualTo(UPDATED_NOI_SAN_XUAT);
        assertThat(testNongSan.getMoTaNS()).isEqualTo(UPDATED_MO_TA_NS);
    }

    @Test
    @Transactional
    void patchNonExistingNongSan() throws Exception {
        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();
        nongSan.setId(count.incrementAndGet());

        // Create the NongSan
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nongSanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNongSan() throws Exception {
        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();
        nongSan.setId(count.incrementAndGet());

        // Create the NongSan
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nongSanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNongSan() throws Exception {
        int databaseSizeBeforeUpdate = nongSanRepository.findAll().size();
        nongSan.setId(count.incrementAndGet());

        // Create the NongSan
        NongSanDTO nongSanDTO = nongSanMapper.toDto(nongSan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNongSanMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nongSanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NongSan in the database
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNongSan() throws Exception {
        // Initialize the database
        nongSanRepository.saveAndFlush(nongSan);

        int databaseSizeBeforeDelete = nongSanRepository.findAll().size();

        // Delete the nongSan
        restNongSanMockMvc
            .perform(delete(ENTITY_API_URL_ID, nongSan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NongSan> nongSanList = nongSanRepository.findAll();
        assertThat(nongSanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
