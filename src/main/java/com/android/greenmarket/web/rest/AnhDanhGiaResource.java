package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.AnhDanhGiaRepository;
import com.android.greenmarket.service.AnhDanhGiaService;
import com.android.greenmarket.service.dto.AnhDanhGiaDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.AnhDanhGia}.
 */
@RestController
@RequestMapping("/api")
public class AnhDanhGiaResource {

    private final Logger log = LoggerFactory.getLogger(AnhDanhGiaResource.class);

    private static final String ENTITY_NAME = "anhDanhGia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnhDanhGiaService anhDanhGiaService;

    private final AnhDanhGiaRepository anhDanhGiaRepository;

    public AnhDanhGiaResource(AnhDanhGiaService anhDanhGiaService, AnhDanhGiaRepository anhDanhGiaRepository) {
        this.anhDanhGiaService = anhDanhGiaService;
        this.anhDanhGiaRepository = anhDanhGiaRepository;
    }

    /**
     * {@code POST  /anh-danh-gias} : Create a new anhDanhGia.
     *
     * @param anhDanhGiaDTO the anhDanhGiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anhDanhGiaDTO, or with status {@code 400 (Bad Request)} if the anhDanhGia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anh-danh-gias")
    public ResponseEntity<AnhDanhGiaDTO> createAnhDanhGia(@Valid @RequestBody AnhDanhGiaDTO anhDanhGiaDTO) throws URISyntaxException {
        log.debug("REST request to save AnhDanhGia : {}", anhDanhGiaDTO);
        if (anhDanhGiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new anhDanhGia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnhDanhGiaDTO result = anhDanhGiaService.save(anhDanhGiaDTO);
        return ResponseEntity
            .created(new URI("/api/anh-danh-gias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anh-danh-gias/:id} : Updates an existing anhDanhGia.
     *
     * @param id the id of the anhDanhGiaDTO to save.
     * @param anhDanhGiaDTO the anhDanhGiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anhDanhGiaDTO,
     * or with status {@code 400 (Bad Request)} if the anhDanhGiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anhDanhGiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anh-danh-gias/{id}")
    public ResponseEntity<AnhDanhGiaDTO> updateAnhDanhGia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnhDanhGiaDTO anhDanhGiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnhDanhGia : {}, {}", id, anhDanhGiaDTO);
        if (anhDanhGiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anhDanhGiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anhDanhGiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnhDanhGiaDTO result = anhDanhGiaService.save(anhDanhGiaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anhDanhGiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /anh-danh-gias/:id} : Partial updates given fields of an existing anhDanhGia, field will ignore if it is null
     *
     * @param id the id of the anhDanhGiaDTO to save.
     * @param anhDanhGiaDTO the anhDanhGiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anhDanhGiaDTO,
     * or with status {@code 400 (Bad Request)} if the anhDanhGiaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anhDanhGiaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anhDanhGiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/anh-danh-gias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnhDanhGiaDTO> partialUpdateAnhDanhGia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnhDanhGiaDTO anhDanhGiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnhDanhGia partially : {}, {}", id, anhDanhGiaDTO);
        if (anhDanhGiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anhDanhGiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anhDanhGiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnhDanhGiaDTO> result = anhDanhGiaService.partialUpdate(anhDanhGiaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anhDanhGiaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anh-danh-gias} : get all the anhDanhGias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anhDanhGias in body.
     */
    @GetMapping("/anh-danh-gias")
    public List<AnhDanhGiaDTO> getAllAnhDanhGias() {
        log.debug("REST request to get all AnhDanhGias");
        return anhDanhGiaService.findAll();
    }

    /**
     * {@code GET  /anh-danh-gias/:id} : get the "id" anhDanhGia.
     *
     * @param id the id of the anhDanhGiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anhDanhGiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anh-danh-gias/{id}")
    public ResponseEntity<AnhDanhGiaDTO> getAnhDanhGia(@PathVariable Long id) {
        log.debug("REST request to get AnhDanhGia : {}", id);
        Optional<AnhDanhGiaDTO> anhDanhGiaDTO = anhDanhGiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anhDanhGiaDTO);
    }

    /**
     * {@code DELETE  /anh-danh-gias/:id} : delete the "id" anhDanhGia.
     *
     * @param id the id of the anhDanhGiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anh-danh-gias/{id}")
    public ResponseEntity<Void> deleteAnhDanhGia(@PathVariable Long id) {
        log.debug("REST request to delete AnhDanhGia : {}", id);
        anhDanhGiaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
