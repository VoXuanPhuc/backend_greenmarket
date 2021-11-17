package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.HinhThucGiaoHangRepository;
import com.android.greenmarket.service.HinhThucGiaoHangService;
import com.android.greenmarket.service.dto.HinhThucGiaoHangDTO;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.android.greenmarket.domain.HinhThucGiaoHang}.
 */
@RestController
@RequestMapping("/api")
public class HinhThucGiaoHangResource {

    private final Logger log = LoggerFactory.getLogger(HinhThucGiaoHangResource.class);

    private static final String ENTITY_NAME = "hinhThucGiaoHang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HinhThucGiaoHangService hinhThucGiaoHangService;

    private final HinhThucGiaoHangRepository hinhThucGiaoHangRepository;

    public HinhThucGiaoHangResource(
        HinhThucGiaoHangService hinhThucGiaoHangService,
        HinhThucGiaoHangRepository hinhThucGiaoHangRepository
    ) {
        this.hinhThucGiaoHangService = hinhThucGiaoHangService;
        this.hinhThucGiaoHangRepository = hinhThucGiaoHangRepository;
    }

    /**
     * {@code POST  /hinh-thuc-giao-hangs} : Create a new hinhThucGiaoHang.
     *
     * @param hinhThucGiaoHangDTO the hinhThucGiaoHangDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hinhThucGiaoHangDTO, or with status {@code 400 (Bad Request)} if the hinhThucGiaoHang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hinh-thuc-giao-hangs")
    public ResponseEntity<HinhThucGiaoHangDTO> createHinhThucGiaoHang(@Valid @RequestBody HinhThucGiaoHangDTO hinhThucGiaoHangDTO)
        throws URISyntaxException {
        log.debug("REST request to save HinhThucGiaoHang : {}", hinhThucGiaoHangDTO);
        if (hinhThucGiaoHangDTO.getId() != null) {
            throw new BadRequestAlertException("A new hinhThucGiaoHang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HinhThucGiaoHangDTO result = hinhThucGiaoHangService.save(hinhThucGiaoHangDTO);
        return ResponseEntity
            .created(new URI("/api/hinh-thuc-giao-hangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hinh-thuc-giao-hangs/:id} : Updates an existing hinhThucGiaoHang.
     *
     * @param id the id of the hinhThucGiaoHangDTO to save.
     * @param hinhThucGiaoHangDTO the hinhThucGiaoHangDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hinhThucGiaoHangDTO,
     * or with status {@code 400 (Bad Request)} if the hinhThucGiaoHangDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hinhThucGiaoHangDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hinh-thuc-giao-hangs/{id}")
    public ResponseEntity<HinhThucGiaoHangDTO> updateHinhThucGiaoHang(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HinhThucGiaoHangDTO hinhThucGiaoHangDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HinhThucGiaoHang : {}, {}", id, hinhThucGiaoHangDTO);
        if (hinhThucGiaoHangDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hinhThucGiaoHangDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hinhThucGiaoHangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HinhThucGiaoHangDTO result = hinhThucGiaoHangService.save(hinhThucGiaoHangDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hinhThucGiaoHangDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hinh-thuc-giao-hangs/:id} : Partial updates given fields of an existing hinhThucGiaoHang, field will ignore if it is null
     *
     * @param id the id of the hinhThucGiaoHangDTO to save.
     * @param hinhThucGiaoHangDTO the hinhThucGiaoHangDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hinhThucGiaoHangDTO,
     * or with status {@code 400 (Bad Request)} if the hinhThucGiaoHangDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hinhThucGiaoHangDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hinhThucGiaoHangDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hinh-thuc-giao-hangs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HinhThucGiaoHangDTO> partialUpdateHinhThucGiaoHang(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HinhThucGiaoHangDTO hinhThucGiaoHangDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HinhThucGiaoHang partially : {}, {}", id, hinhThucGiaoHangDTO);
        if (hinhThucGiaoHangDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hinhThucGiaoHangDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hinhThucGiaoHangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HinhThucGiaoHangDTO> result = hinhThucGiaoHangService.partialUpdate(hinhThucGiaoHangDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hinhThucGiaoHangDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hinh-thuc-giao-hangs} : get all the hinhThucGiaoHangs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hinhThucGiaoHangs in body.
     */
    @GetMapping("/hinh-thuc-giao-hangs")
    public List<HinhThucGiaoHangDTO> getAllHinhThucGiaoHangs() {
        log.debug("REST request to get all HinhThucGiaoHangs");
        return hinhThucGiaoHangService.findAll();
    }

    /**
     * {@code GET  /hinh-thuc-giao-hangs/:id} : get the "id" hinhThucGiaoHang.
     *
     * @param id the id of the hinhThucGiaoHangDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hinhThucGiaoHangDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hinh-thuc-giao-hangs/{id}")
    public ResponseEntity<HinhThucGiaoHangDTO> getHinhThucGiaoHang(@PathVariable Long id) {
        log.debug("REST request to get HinhThucGiaoHang : {}", id);
        Optional<HinhThucGiaoHangDTO> hinhThucGiaoHangDTO = hinhThucGiaoHangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hinhThucGiaoHangDTO);
    }

    /**
     * {@code DELETE  /hinh-thuc-giao-hangs/:id} : delete the "id" hinhThucGiaoHang.
     *
     * @param id the id of the hinhThucGiaoHangDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hinh-thuc-giao-hangs/{id}")
    public ResponseEntity<Void> deleteHinhThucGiaoHang(@PathVariable Long id) {
        log.debug("REST request to delete HinhThucGiaoHang : {}", id);
        hinhThucGiaoHangService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
