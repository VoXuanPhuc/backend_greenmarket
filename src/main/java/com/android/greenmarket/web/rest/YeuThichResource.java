package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.YeuThichRepository;
import com.android.greenmarket.service.YeuThichService;
import com.android.greenmarket.service.dto.YeuThichDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.YeuThich}.
 */
@RestController
@RequestMapping("/api")
public class YeuThichResource {

    private final Logger log = LoggerFactory.getLogger(YeuThichResource.class);

    private static final String ENTITY_NAME = "yeuThich";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final YeuThichService yeuThichService;

    private final YeuThichRepository yeuThichRepository;

    public YeuThichResource(YeuThichService yeuThichService, YeuThichRepository yeuThichRepository) {
        this.yeuThichService = yeuThichService;
        this.yeuThichRepository = yeuThichRepository;
    }

    /**
     * {@code POST  /yeu-thiches} : Create a new yeuThich.
     *
     * @param yeuThichDTO the yeuThichDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new yeuThichDTO, or with status {@code 400 (Bad Request)} if the yeuThich has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/yeu-thiches")
    public ResponseEntity<YeuThichDTO> createYeuThich(@RequestBody YeuThichDTO yeuThichDTO) throws URISyntaxException {
        log.debug("REST request to save YeuThich : {}", yeuThichDTO);
        if (yeuThichDTO.getId() != null) {
            throw new BadRequestAlertException("A new yeuThich cannot already have an ID", ENTITY_NAME, "idexists");
        }
        YeuThichDTO result = yeuThichService.save(yeuThichDTO);
        return ResponseEntity
            .created(new URI("/api/yeu-thiches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /yeu-thiches/:id} : Updates an existing yeuThich.
     *
     * @param id the id of the yeuThichDTO to save.
     * @param yeuThichDTO the yeuThichDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated yeuThichDTO,
     * or with status {@code 400 (Bad Request)} if the yeuThichDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the yeuThichDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/yeu-thiches/{id}")
    public ResponseEntity<YeuThichDTO> updateYeuThich(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody YeuThichDTO yeuThichDTO
    ) throws URISyntaxException {
        log.debug("REST request to update YeuThich : {}, {}", id, yeuThichDTO);
        if (yeuThichDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, yeuThichDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!yeuThichRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        YeuThichDTO result = yeuThichService.save(yeuThichDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, yeuThichDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /yeu-thiches/:id} : Partial updates given fields of an existing yeuThich, field will ignore if it is null
     *
     * @param id the id of the yeuThichDTO to save.
     * @param yeuThichDTO the yeuThichDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated yeuThichDTO,
     * or with status {@code 400 (Bad Request)} if the yeuThichDTO is not valid,
     * or with status {@code 404 (Not Found)} if the yeuThichDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the yeuThichDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/yeu-thiches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<YeuThichDTO> partialUpdateYeuThich(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody YeuThichDTO yeuThichDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update YeuThich partially : {}, {}", id, yeuThichDTO);
        if (yeuThichDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, yeuThichDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!yeuThichRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<YeuThichDTO> result = yeuThichService.partialUpdate(yeuThichDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, yeuThichDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /yeu-thiches} : get all the yeuThiches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of yeuThiches in body.
     */
    @GetMapping("/yeu-thiches")
    public List<YeuThichDTO> getAllYeuThiches() {
        log.debug("REST request to get all YeuThiches");
        return yeuThichService.findAll();
    }

    /**
     * {@code GET  /yeu-thiches/:id} : get the "id" yeuThich.
     *
     * @param id the id of the yeuThichDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the yeuThichDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/yeu-thiches/{id}")
    public ResponseEntity<YeuThichDTO> getYeuThich(@PathVariable Long id) {
        log.debug("REST request to get YeuThich : {}", id);
        Optional<YeuThichDTO> yeuThichDTO = yeuThichService.findOne(id);
        return ResponseUtil.wrapOrNotFound(yeuThichDTO);
    }

    /**
     * {@code DELETE  /yeu-thiches/:id} : delete the "id" yeuThich.
     *
     * @param id the id of the yeuThichDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/yeu-thiches/{id}")
    public ResponseEntity<Void> deleteYeuThich(@PathVariable Long id) {
        log.debug("REST request to delete YeuThich : {}", id);
        yeuThichService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
