package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.DanhMucRepository;
import com.android.greenmarket.service.DanhMucService;
import com.android.greenmarket.service.dto.DanhMucDTO;
import com.android.greenmarket.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.android.greenmarket.domain.DanhMuc}.
 */
@RestController
@RequestMapping("/api")
public class DanhMucResource {

    private final Logger log = LoggerFactory.getLogger(DanhMucResource.class);

    private static final String ENTITY_NAME = "danhMuc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DanhMucService danhMucService;

    private final DanhMucRepository danhMucRepository;

    public DanhMucResource(DanhMucService danhMucService, DanhMucRepository danhMucRepository) {
        this.danhMucService = danhMucService;
        this.danhMucRepository = danhMucRepository;
    }

    /**
     * {@code POST  /danh-mucs} : Create a new danhMuc.
     *
     * @param danhMucDTO the danhMucDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new danhMucDTO, or with status {@code 400 (Bad Request)} if the danhMuc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/danh-mucs")
    public ResponseEntity<DanhMucDTO> createDanhMuc(@RequestBody DanhMucDTO danhMucDTO) throws URISyntaxException {
        log.debug("REST request to save DanhMuc : {}", danhMucDTO);
        if (danhMucDTO.getId() != null) {
            throw new BadRequestAlertException("A new danhMuc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DanhMucDTO result = danhMucService.save(danhMucDTO);
        return ResponseEntity
            .created(new URI("/api/danh-mucs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /danh-mucs/:id} : Updates an existing danhMuc.
     *
     * @param id the id of the danhMucDTO to save.
     * @param danhMucDTO the danhMucDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated danhMucDTO,
     * or with status {@code 400 (Bad Request)} if the danhMucDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the danhMucDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/danh-mucs/{id}")
    public ResponseEntity<DanhMucDTO> updateDanhMuc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DanhMucDTO danhMucDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DanhMuc : {}, {}", id, danhMucDTO);
        if (danhMucDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, danhMucDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!danhMucRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DanhMucDTO result = danhMucService.save(danhMucDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, danhMucDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /danh-mucs/:id} : Partial updates given fields of an existing danhMuc, field will ignore if it is null
     *
     * @param id the id of the danhMucDTO to save.
     * @param danhMucDTO the danhMucDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated danhMucDTO,
     * or with status {@code 400 (Bad Request)} if the danhMucDTO is not valid,
     * or with status {@code 404 (Not Found)} if the danhMucDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the danhMucDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/danh-mucs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DanhMucDTO> partialUpdateDanhMuc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DanhMucDTO danhMucDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DanhMuc partially : {}, {}", id, danhMucDTO);
        if (danhMucDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, danhMucDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!danhMucRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DanhMucDTO> result = danhMucService.partialUpdate(danhMucDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, danhMucDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /danh-mucs} : get all the danhMucs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of danhMucs in body.
     */
    @GetMapping("/danh-mucs")
    public List<DanhMucDTO> getAllDanhMucs() {
        log.debug("REST request to get all DanhMucs");
        return danhMucService.findAll();
    }

    /**
     * {@code GET  /danh-mucs/:id} : get the "id" danhMuc.
     *
     * @param id the id of the danhMucDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the danhMucDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/danh-mucs/{id}")
    public ResponseEntity<DanhMucDTO> getDanhMuc(@PathVariable Long id) {
        log.debug("REST request to get DanhMuc : {}", id);
        Optional<DanhMucDTO> danhMucDTO = danhMucService.findOne(id);
        return ResponseUtil.wrapOrNotFound(danhMucDTO);
    }

    /**
     * {@code DELETE  /danh-mucs/:id} : delete the "id" danhMuc.
     *
     * @param id the id of the danhMucDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/danh-mucs/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Long id) {
        log.debug("REST request to delete DanhMuc : {}", id);
        danhMucService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
