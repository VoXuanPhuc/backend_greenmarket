package com.android.greenmarket.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.android.greenmarket.IntegrationTest;
import com.android.greenmarket.domain.KhachHang;
import com.android.greenmarket.repository.KhachHangRepository;
import com.android.greenmarket.service.dto.KhachHangDTO;
import com.android.greenmarket.service.mapper.KhachHangMapper;
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
 * Integration tests for the {@link KhachHangResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KhachHangResourceIT {

    private static final String DEFAULT_HO_TEN_KH = "AAAAAAAAAA";
    private static final String UPDATED_HO_TEN_KH = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_DANG_NHAP = "AAAAAAAAAA";
    private static final String UPDATED_TEN_DANG_NHAP = "BBBBBBBBBB";

    private static final String DEFAULT_MATKHAU = "AAAAAAAAAA";
    private static final String UPDATED_MATKHAU = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SDT = "AAAAAAAAAA";
    private static final String UPDATED_SDT = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAY_SINH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_SINH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GIOITINH = "AAAAAAAAAA";
    private static final String UPDATED_GIOITINH = "BBBBBBBBBB";

    private static final String DEFAULT_CHITIETDIACHI = "AAAAAAAAAA";
    private static final String UPDATED_CHITIETDIACHI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/khach-hangs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private KhachHangMapper khachHangMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKhachHangMockMvc;

    private KhachHang khachHang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KhachHang createEntity(EntityManager em) {
        KhachHang khachHang = new KhachHang()
            .hoTenKH(DEFAULT_HO_TEN_KH)
            .tenDangNhap(DEFAULT_TEN_DANG_NHAP)
            .matkhau(DEFAULT_MATKHAU)
            .email(DEFAULT_EMAIL)
            .sdt(DEFAULT_SDT)
            .ngaySinh(DEFAULT_NGAY_SINH)
            .gioitinh(DEFAULT_GIOITINH)
            .chitietdiachi(DEFAULT_CHITIETDIACHI);
        return khachHang;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KhachHang createUpdatedEntity(EntityManager em) {
        KhachHang khachHang = new KhachHang()
            .hoTenKH(UPDATED_HO_TEN_KH)
            .tenDangNhap(UPDATED_TEN_DANG_NHAP)
            .matkhau(UPDATED_MATKHAU)
            .email(UPDATED_EMAIL)
            .sdt(UPDATED_SDT)
            .ngaySinh(UPDATED_NGAY_SINH)
            .gioitinh(UPDATED_GIOITINH)
            .chitietdiachi(UPDATED_CHITIETDIACHI);
        return khachHang;
    }

    @BeforeEach
    public void initTest() {
        khachHang = createEntity(em);
    }

    @Test
    @Transactional
    void createKhachHang() throws Exception {
        int databaseSizeBeforeCreate = khachHangRepository.findAll().size();
        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);
        restKhachHangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isCreated());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeCreate + 1);
        KhachHang testKhachHang = khachHangList.get(khachHangList.size() - 1);
        assertThat(testKhachHang.getHoTenKH()).isEqualTo(DEFAULT_HO_TEN_KH);
        assertThat(testKhachHang.getTenDangNhap()).isEqualTo(DEFAULT_TEN_DANG_NHAP);
        assertThat(testKhachHang.getMatkhau()).isEqualTo(DEFAULT_MATKHAU);
        assertThat(testKhachHang.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKhachHang.getSdt()).isEqualTo(DEFAULT_SDT);
        assertThat(testKhachHang.getNgaySinh()).isEqualTo(DEFAULT_NGAY_SINH);
        assertThat(testKhachHang.getGioitinh()).isEqualTo(DEFAULT_GIOITINH);
        assertThat(testKhachHang.getChitietdiachi()).isEqualTo(DEFAULT_CHITIETDIACHI);
    }

    @Test
    @Transactional
    void createKhachHangWithExistingId() throws Exception {
        // Create the KhachHang with an existing ID
        khachHang.setId(1L);
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        int databaseSizeBeforeCreate = khachHangRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKhachHangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMatkhauIsRequired() throws Exception {
        int databaseSizeBeforeTest = khachHangRepository.findAll().size();
        // set the field null
        khachHang.setMatkhau(null);

        // Create the KhachHang, which fails.
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        restKhachHangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isBadRequest());

        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = khachHangRepository.findAll().size();
        // set the field null
        khachHang.setEmail(null);

        // Create the KhachHang, which fails.
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        restKhachHangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isBadRequest());

        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSdtIsRequired() throws Exception {
        int databaseSizeBeforeTest = khachHangRepository.findAll().size();
        // set the field null
        khachHang.setSdt(null);

        // Create the KhachHang, which fails.
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        restKhachHangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isBadRequest());

        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgaySinhIsRequired() throws Exception {
        int databaseSizeBeforeTest = khachHangRepository.findAll().size();
        // set the field null
        khachHang.setNgaySinh(null);

        // Create the KhachHang, which fails.
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        restKhachHangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isBadRequest());

        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKhachHangs() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        // Get all the khachHangList
        restKhachHangMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(khachHang.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoTenKH").value(hasItem(DEFAULT_HO_TEN_KH)))
            .andExpect(jsonPath("$.[*].tenDangNhap").value(hasItem(DEFAULT_TEN_DANG_NHAP)))
            .andExpect(jsonPath("$.[*].matkhau").value(hasItem(DEFAULT_MATKHAU)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sdt").value(hasItem(DEFAULT_SDT)))
            .andExpect(jsonPath("$.[*].ngaySinh").value(hasItem(DEFAULT_NGAY_SINH.toString())))
            .andExpect(jsonPath("$.[*].gioitinh").value(hasItem(DEFAULT_GIOITINH)))
            .andExpect(jsonPath("$.[*].chitietdiachi").value(hasItem(DEFAULT_CHITIETDIACHI)));
    }

    @Test
    @Transactional
    void getKhachHang() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        // Get the khachHang
        restKhachHangMockMvc
            .perform(get(ENTITY_API_URL_ID, khachHang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(khachHang.getId().intValue()))
            .andExpect(jsonPath("$.hoTenKH").value(DEFAULT_HO_TEN_KH))
            .andExpect(jsonPath("$.tenDangNhap").value(DEFAULT_TEN_DANG_NHAP))
            .andExpect(jsonPath("$.matkhau").value(DEFAULT_MATKHAU))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.sdt").value(DEFAULT_SDT))
            .andExpect(jsonPath("$.ngaySinh").value(DEFAULT_NGAY_SINH.toString()))
            .andExpect(jsonPath("$.gioitinh").value(DEFAULT_GIOITINH))
            .andExpect(jsonPath("$.chitietdiachi").value(DEFAULT_CHITIETDIACHI));
    }

    @Test
    @Transactional
    void getNonExistingKhachHang() throws Exception {
        // Get the khachHang
        restKhachHangMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKhachHang() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();

        // Update the khachHang
        KhachHang updatedKhachHang = khachHangRepository.findById(khachHang.getId()).get();
        // Disconnect from session so that the updates on updatedKhachHang are not directly saved in db
        em.detach(updatedKhachHang);
        updatedKhachHang
            .hoTenKH(UPDATED_HO_TEN_KH)
            .tenDangNhap(UPDATED_TEN_DANG_NHAP)
            .matkhau(UPDATED_MATKHAU)
            .email(UPDATED_EMAIL)
            .sdt(UPDATED_SDT)
            .ngaySinh(UPDATED_NGAY_SINH)
            .gioitinh(UPDATED_GIOITINH)
            .chitietdiachi(UPDATED_CHITIETDIACHI);
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(updatedKhachHang);

        restKhachHangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, khachHangDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khachHangDTO))
            )
            .andExpect(status().isOk());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
        KhachHang testKhachHang = khachHangList.get(khachHangList.size() - 1);
        assertThat(testKhachHang.getHoTenKH()).isEqualTo(UPDATED_HO_TEN_KH);
        assertThat(testKhachHang.getTenDangNhap()).isEqualTo(UPDATED_TEN_DANG_NHAP);
        assertThat(testKhachHang.getMatkhau()).isEqualTo(UPDATED_MATKHAU);
        assertThat(testKhachHang.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKhachHang.getSdt()).isEqualTo(UPDATED_SDT);
        assertThat(testKhachHang.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
        assertThat(testKhachHang.getGioitinh()).isEqualTo(UPDATED_GIOITINH);
        assertThat(testKhachHang.getChitietdiachi()).isEqualTo(UPDATED_CHITIETDIACHI);
    }

    @Test
    @Transactional
    void putNonExistingKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();
        khachHang.setId(count.incrementAndGet());

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhachHangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, khachHangDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khachHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();
        khachHang.setId(count.incrementAndGet());

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhachHangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khachHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();
        khachHang.setId(count.incrementAndGet());

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhachHangMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKhachHangWithPatch() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();

        // Update the khachHang using partial update
        KhachHang partialUpdatedKhachHang = new KhachHang();
        partialUpdatedKhachHang.setId(khachHang.getId());

        partialUpdatedKhachHang
            .hoTenKH(UPDATED_HO_TEN_KH)
            .tenDangNhap(UPDATED_TEN_DANG_NHAP)
            .sdt(UPDATED_SDT)
            .gioitinh(UPDATED_GIOITINH)
            .chitietdiachi(UPDATED_CHITIETDIACHI);

        restKhachHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhachHang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhachHang))
            )
            .andExpect(status().isOk());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
        KhachHang testKhachHang = khachHangList.get(khachHangList.size() - 1);
        assertThat(testKhachHang.getHoTenKH()).isEqualTo(UPDATED_HO_TEN_KH);
        assertThat(testKhachHang.getTenDangNhap()).isEqualTo(UPDATED_TEN_DANG_NHAP);
        assertThat(testKhachHang.getMatkhau()).isEqualTo(DEFAULT_MATKHAU);
        assertThat(testKhachHang.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKhachHang.getSdt()).isEqualTo(UPDATED_SDT);
        assertThat(testKhachHang.getNgaySinh()).isEqualTo(DEFAULT_NGAY_SINH);
        assertThat(testKhachHang.getGioitinh()).isEqualTo(UPDATED_GIOITINH);
        assertThat(testKhachHang.getChitietdiachi()).isEqualTo(UPDATED_CHITIETDIACHI);
    }

    @Test
    @Transactional
    void fullUpdateKhachHangWithPatch() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();

        // Update the khachHang using partial update
        KhachHang partialUpdatedKhachHang = new KhachHang();
        partialUpdatedKhachHang.setId(khachHang.getId());

        partialUpdatedKhachHang
            .hoTenKH(UPDATED_HO_TEN_KH)
            .tenDangNhap(UPDATED_TEN_DANG_NHAP)
            .matkhau(UPDATED_MATKHAU)
            .email(UPDATED_EMAIL)
            .sdt(UPDATED_SDT)
            .ngaySinh(UPDATED_NGAY_SINH)
            .gioitinh(UPDATED_GIOITINH)
            .chitietdiachi(UPDATED_CHITIETDIACHI);

        restKhachHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhachHang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhachHang))
            )
            .andExpect(status().isOk());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
        KhachHang testKhachHang = khachHangList.get(khachHangList.size() - 1);
        assertThat(testKhachHang.getHoTenKH()).isEqualTo(UPDATED_HO_TEN_KH);
        assertThat(testKhachHang.getTenDangNhap()).isEqualTo(UPDATED_TEN_DANG_NHAP);
        assertThat(testKhachHang.getMatkhau()).isEqualTo(UPDATED_MATKHAU);
        assertThat(testKhachHang.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKhachHang.getSdt()).isEqualTo(UPDATED_SDT);
        assertThat(testKhachHang.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
        assertThat(testKhachHang.getGioitinh()).isEqualTo(UPDATED_GIOITINH);
        assertThat(testKhachHang.getChitietdiachi()).isEqualTo(UPDATED_CHITIETDIACHI);
    }

    @Test
    @Transactional
    void patchNonExistingKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();
        khachHang.setId(count.incrementAndGet());

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhachHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, khachHangDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khachHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();
        khachHang.setId(count.incrementAndGet());

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhachHangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khachHangDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();
        khachHang.setId(count.incrementAndGet());

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhachHangMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(khachHangDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKhachHang() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        int databaseSizeBeforeDelete = khachHangRepository.findAll().size();

        // Delete the khachHang
        restKhachHangMockMvc
            .perform(delete(ENTITY_API_URL_ID, khachHang.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
