package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.TinhTPRepository;
import com.android.greenmarket.service.TinhTPService;
import com.android.greenmarket.service.dto.TinhTPDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.TinhTP}.
 */
@RestController
@RequestMapping("/api")
public class TinhTPResource {

    private final Logger log = LoggerFactory.getLogger(TinhTPResource.class);

    private static final String ENTITY_NAME = "tinhTP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TinhTPService tinhTPService;

    private final TinhTPRepository tinhTPRepository;

    public TinhTPResource(TinhTPService tinhTPService, TinhTPRepository tinhTPRepository) {
        this.tinhTPService = tinhTPService;
        this.tinhTPRepository = tinhTPRepository;
    }

    /**
     * {@code POST  /tinh-tps} : Create a new tinhTP.
     *
     * @param tinhTPDTO the tinhTPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tinhTPDTO, or with status {@code 400 (Bad Request)} if the tinhTP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tinh-tps")
    public ResponseEntity<TinhTPDTO> createTinhTP(@RequestBody TinhTPDTO tinhTPDTO) throws URISyntaxException {
        log.debug("REST request to save TinhTP : {}", tinhTPDTO);
        if (tinhTPDTO.getId() != null) {
            throw new BadRequestAlertException("A new tinhTP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TinhTPDTO result = tinhTPService.save(tinhTPDTO);
        return ResponseEntity
            .created(new URI("/api/tinh-tps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tinh-tps/:id} : Updates an existing tinhTP.
     *
     * @param id the id of the tinhTPDTO to save.
     * @param tinhTPDTO the tinhTPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tinhTPDTO,
     * or with status {@code 400 (Bad Request)} if the tinhTPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tinhTPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tinh-tps/{id}")
    public ResponseEntity<TinhTPDTO> updateTinhTP(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TinhTPDTO tinhTPDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TinhTP : {}, {}", id, tinhTPDTO);
        if (tinhTPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tinhTPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tinhTPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TinhTPDTO result = tinhTPService.save(tinhTPDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tinhTPDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tinh-tps/:id} : Partial updates given fields of an existing tinhTP, field will ignore if it is null
     *
     * @param id the id of the tinhTPDTO to save.
     * @param tinhTPDTO the tinhTPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tinhTPDTO,
     * or with status {@code 400 (Bad Request)} if the tinhTPDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tinhTPDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tinhTPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tinh-tps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TinhTPDTO> partialUpdateTinhTP(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TinhTPDTO tinhTPDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TinhTP partially : {}, {}", id, tinhTPDTO);
        if (tinhTPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tinhTPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tinhTPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TinhTPDTO> result = tinhTPService.partialUpdate(tinhTPDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tinhTPDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tinh-tps} : get all the tinhTPS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tinhTPS in body.
     */
    @GetMapping("/tinh-tps")
    public List<TinhTPDTO> getAllTinhTPS() {
        log.debug("REST request to get all TinhTPS");
        return tinhTPService.findAll();
    }

    /**
     * {@code GET  /tinh-tps/:id} : get the "id" tinhTP.
     *
     * @param id the id of the tinhTPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tinhTPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tinh-tps/{id}")
    public ResponseEntity<TinhTPDTO> getTinhTP(@PathVariable Long id) {
        log.debug("REST request to get TinhTP : {}", id);
        Optional<TinhTPDTO> tinhTPDTO = tinhTPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tinhTPDTO);
    }

    /**
     * {@code DELETE  /tinh-tps/:id} : delete the "id" tinhTP.
     *
     * @param id the id of the tinhTPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tinh-tps/{id}")
    public ResponseEntity<Void> deleteTinhTP(@PathVariable Long id) {
        log.debug("REST request to delete TinhTP : {}", id);
        tinhTPService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
