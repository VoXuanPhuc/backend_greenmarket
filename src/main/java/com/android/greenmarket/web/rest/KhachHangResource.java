package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.KhachHangRepository;
import com.android.greenmarket.service.KhachHangService;
import com.android.greenmarket.service.dto.KhachHangDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.KhachHang}.
 */
@RestController
@RequestMapping("/api")
public class KhachHangResource {

    private final Logger log = LoggerFactory.getLogger(KhachHangResource.class);

    private static final String ENTITY_NAME = "khachHang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KhachHangService khachHangService;

    private final KhachHangRepository khachHangRepository;

    public KhachHangResource(KhachHangService khachHangService, KhachHangRepository khachHangRepository) {
        this.khachHangService = khachHangService;
        this.khachHangRepository = khachHangRepository;
    }

    /**
     * {@code POST  /khach-hangs} : Create a new khachHang.
     *
     * @param khachHangDTO the khachHangDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new khachHangDTO, or with status {@code 400 (Bad Request)} if the khachHang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/khach-hangs")
    public ResponseEntity<KhachHangDTO> createKhachHang(@Valid @RequestBody KhachHangDTO khachHangDTO) throws URISyntaxException {
        log.debug("REST request to save KhachHang : {}", khachHangDTO);
        if (khachHangDTO.getId() != null) {
            throw new BadRequestAlertException("A new khachHang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KhachHangDTO result = khachHangService.save(khachHangDTO);
        return ResponseEntity
            .created(new URI("/api/khach-hangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /khach-hangs/:id} : Updates an existing khachHang.
     *
     * @param id the id of the khachHangDTO to save.
     * @param khachHangDTO the khachHangDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khachHangDTO,
     * or with status {@code 400 (Bad Request)} if the khachHangDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the khachHangDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/khach-hangs/{id}")
    public ResponseEntity<KhachHangDTO> updateKhachHang(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KhachHangDTO khachHangDTO
    ) throws URISyntaxException {
        log.debug("REST request to update KhachHang : {}, {}", id, khachHangDTO);
        if (khachHangDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khachHangDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khachHangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KhachHangDTO result = khachHangService.save(khachHangDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, khachHangDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /khach-hangs/:id} : Partial updates given fields of an existing khachHang, field will ignore if it is null
     *
     * @param id the id of the khachHangDTO to save.
     * @param khachHangDTO the khachHangDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khachHangDTO,
     * or with status {@code 400 (Bad Request)} if the khachHangDTO is not valid,
     * or with status {@code 404 (Not Found)} if the khachHangDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the khachHangDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/khach-hangs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<KhachHangDTO> partialUpdateKhachHang(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KhachHangDTO khachHangDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update KhachHang partially : {}, {}", id, khachHangDTO);
        if (khachHangDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khachHangDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khachHangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KhachHangDTO> result = khachHangService.partialUpdate(khachHangDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, khachHangDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /khach-hangs} : get all the khachHangs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of khachHangs in body.
     */
    @GetMapping("/khach-hangs")
    public List<KhachHangDTO> getAllKhachHangs() {
        log.debug("REST request to get all KhachHangs");
        return khachHangService.findAll();
    }

    /**
     * {@code GET  /khach-hangs/:id} : get the "id" khachHang.
     *
     * @param id the id of the khachHangDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the khachHangDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/khach-hangs/{id}")
    public ResponseEntity<KhachHangDTO> getKhachHang(@PathVariable Long id) {
        log.debug("REST request to get KhachHang : {}", id);
        Optional<KhachHangDTO> khachHangDTO = khachHangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(khachHangDTO);
    }

    /**
     * {@code DELETE  /khach-hangs/:id} : delete the "id" khachHang.
     *
     * @param id the id of the khachHangDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/khach-hangs/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Long id) {
        log.debug("REST request to delete KhachHang : {}", id);
        khachHangService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
