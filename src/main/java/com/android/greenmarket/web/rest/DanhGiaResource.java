package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.DanhGiaRepository;
import com.android.greenmarket.service.DanhGiaService;
import com.android.greenmarket.service.dto.DanhGiaDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.DanhGia}.
 */
@RestController
@RequestMapping("/api")
public class DanhGiaResource {

    private final Logger log = LoggerFactory.getLogger(DanhGiaResource.class);

    private static final String ENTITY_NAME = "danhGia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DanhGiaService danhGiaService;

    private final DanhGiaRepository danhGiaRepository;

    public DanhGiaResource(DanhGiaService danhGiaService, DanhGiaRepository danhGiaRepository) {
        this.danhGiaService = danhGiaService;
        this.danhGiaRepository = danhGiaRepository;
    }

    /**
     * {@code POST  /danh-gias} : Create a new danhGia.
     *
     * @param danhGiaDTO the danhGiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new danhGiaDTO, or with status {@code 400 (Bad Request)} if the danhGia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/danh-gias")
    public ResponseEntity<DanhGiaDTO> createDanhGia(@Valid @RequestBody DanhGiaDTO danhGiaDTO) throws URISyntaxException {
        log.debug("REST request to save DanhGia : {}", danhGiaDTO);
        if (danhGiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new danhGia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DanhGiaDTO result = danhGiaService.save(danhGiaDTO);
        return ResponseEntity
            .created(new URI("/api/danh-gias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /danh-gias/:id} : Updates an existing danhGia.
     *
     * @param id the id of the danhGiaDTO to save.
     * @param danhGiaDTO the danhGiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated danhGiaDTO,
     * or with status {@code 400 (Bad Request)} if the danhGiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the danhGiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/danh-gias/{id}")
    public ResponseEntity<DanhGiaDTO> updateDanhGia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DanhGiaDTO danhGiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DanhGia : {}, {}", id, danhGiaDTO);
        if (danhGiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, danhGiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!danhGiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DanhGiaDTO result = danhGiaService.save(danhGiaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, danhGiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /danh-gias/:id} : Partial updates given fields of an existing danhGia, field will ignore if it is null
     *
     * @param id the id of the danhGiaDTO to save.
     * @param danhGiaDTO the danhGiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated danhGiaDTO,
     * or with status {@code 400 (Bad Request)} if the danhGiaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the danhGiaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the danhGiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/danh-gias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DanhGiaDTO> partialUpdateDanhGia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DanhGiaDTO danhGiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DanhGia partially : {}, {}", id, danhGiaDTO);
        if (danhGiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, danhGiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!danhGiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DanhGiaDTO> result = danhGiaService.partialUpdate(danhGiaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, danhGiaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /danh-gias} : get all the danhGias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of danhGias in body.
     */
    @GetMapping("/danh-gias")
    public List<DanhGiaDTO> getAllDanhGias() {
        log.debug("REST request to get all DanhGias");
        return danhGiaService.findAll();
    }

    /**
     * {@code GET  /danh-gias/:id} : get the "id" danhGia.
     *
     * @param id the id of the danhGiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the danhGiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/danh-gias/{id}")
    public ResponseEntity<DanhGiaDTO> getDanhGia(@PathVariable Long id) {
        log.debug("REST request to get DanhGia : {}", id);
        Optional<DanhGiaDTO> danhGiaDTO = danhGiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(danhGiaDTO);
    }

    /**
     * {@code DELETE  /danh-gias/:id} : delete the "id" danhGia.
     *
     * @param id the id of the danhGiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/danh-gias/{id}")
    public ResponseEntity<Void> deleteDanhGia(@PathVariable Long id) {
        log.debug("REST request to delete DanhGia : {}", id);
        danhGiaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
