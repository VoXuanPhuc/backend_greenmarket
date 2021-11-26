package com.android.greenmarket.web.rest;

import com.android.greenmarket.domain.DanhMuc;
import com.android.greenmarket.repository.NongSanRepository;
import com.android.greenmarket.service.DanhMucService;
import com.android.greenmarket.service.NongSanService;
import com.android.greenmarket.service.dto.NongSanDTO;
import com.android.greenmarket.service.mapper.DanhMucMapper;
import com.android.greenmarket.service.mapper.NongSanMapper;
import com.android.greenmarket.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.android.greenmarket.domain.NongSan}.
 */
@RestController
@RequestMapping("/api")
public class NongSanResource {

    private final Logger log = LoggerFactory.getLogger(NongSanResource.class);

    private static final String ENTITY_NAME = "nongSan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NongSanService nongSanService;

    private final NongSanRepository nongSanRepository;

    @Autowired
    NongSanMapper nongSanMapper;

    @Autowired
    DanhMucService danhMucService;

    @Autowired
    DanhMucMapper danhMucMapper;

    public NongSanResource(NongSanService nongSanService, NongSanRepository nongSanRepository) {
        this.nongSanService = nongSanService;
        this.nongSanRepository = nongSanRepository;
    }

    /**
     * {@code POST  /nong-sans} : Create a new nongSan.
     *
     * @param nongSanDTO the nongSanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nongSanDTO, or with status {@code 400 (Bad Request)} if the nongSan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nong-sans")
    public ResponseEntity<NongSanDTO> createNongSan(@Valid @RequestBody NongSanDTO nongSanDTO) throws URISyntaxException {
        log.debug("REST request to save NongSan : {}", nongSanDTO);
        if (nongSanDTO.getId() != null) {
            throw new BadRequestAlertException("A new nongSan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NongSanDTO result = nongSanService.save(nongSanDTO);
        return ResponseEntity
            .created(new URI("/api/nong-sans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nong-sans/:id} : Updates an existing nongSan.
     *
     * @param id the id of the nongSanDTO to save.
     * @param nongSanDTO the nongSanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nongSanDTO,
     * or with status {@code 400 (Bad Request)} if the nongSanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nongSanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nong-sans/{id}")
    public ResponseEntity<NongSanDTO> updateNongSan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NongSanDTO nongSanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NongSan : {}, {}", id, nongSanDTO);
        if (nongSanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nongSanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nongSanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NongSanDTO result = nongSanService.save(nongSanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nongSanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nong-sans/:id} : Partial updates given fields of an existing nongSan, field will ignore if it is null
     *
     * @param id the id of the nongSanDTO to save.
     * @param nongSanDTO the nongSanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nongSanDTO,
     * or with status {@code 400 (Bad Request)} if the nongSanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nongSanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nongSanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nong-sans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NongSanDTO> partialUpdateNongSan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NongSanDTO nongSanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NongSan partially : {}, {}", id, nongSanDTO);
        if (nongSanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nongSanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nongSanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NongSanDTO> result = nongSanService.partialUpdate(nongSanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nongSanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nong-sans} : get all the nongSans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nongSans in body.
     */
    @GetMapping("/nong-sans")
    public List<NongSanDTO> getAllNongSans() {
        log.debug("REST request to get all NongSans");
        return nongSanService.findAll();
    }

    /**
     * {@code GET  /nong-sans/:id} : get the "id" nongSan.
     *
     * @param id the id of the nongSanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nongSanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nong-sans/{id}")
    public ResponseEntity<NongSanDTO> getNongSan(@PathVariable Long id) {
        log.debug("REST request to get NongSan : {}", id);
        Optional<NongSanDTO> nongSanDTO = nongSanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nongSanDTO);
    }

    /**
     * {@code DELETE  /nong-sans/:id} : delete the "id" nongSan.
     *
     * @param id the id of the nongSanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nong-sans/{id}")
    public ResponseEntity<Void> deleteNongSan(@PathVariable Long id) {
        log.debug("REST request to delete NongSan : {}", id);
        nongSanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/nong-sans-danh-muc/{idDanhmuc}")
    public List<NongSanDTO> getNongSanByDM(@PathVariable Long idDanhmuc) {
        DanhMuc dm = danhMucMapper.toEntity(danhMucService.findOne(idDanhmuc).get());
        return nongSanMapper.toDto(nongSanRepository.findByDanhmuc(dm));
    }
}
