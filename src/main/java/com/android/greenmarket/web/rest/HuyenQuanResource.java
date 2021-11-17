package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.HuyenQuanRepository;
import com.android.greenmarket.service.HuyenQuanService;
import com.android.greenmarket.service.dto.HuyenQuanDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.HuyenQuan}.
 */
@RestController
@RequestMapping("/api")
public class HuyenQuanResource {

    private final Logger log = LoggerFactory.getLogger(HuyenQuanResource.class);

    private static final String ENTITY_NAME = "huyenQuan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HuyenQuanService huyenQuanService;

    private final HuyenQuanRepository huyenQuanRepository;

    public HuyenQuanResource(HuyenQuanService huyenQuanService, HuyenQuanRepository huyenQuanRepository) {
        this.huyenQuanService = huyenQuanService;
        this.huyenQuanRepository = huyenQuanRepository;
    }

    /**
     * {@code POST  /huyen-quans} : Create a new huyenQuan.
     *
     * @param huyenQuanDTO the huyenQuanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new huyenQuanDTO, or with status {@code 400 (Bad Request)} if the huyenQuan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/huyen-quans")
    public ResponseEntity<HuyenQuanDTO> createHuyenQuan(@RequestBody HuyenQuanDTO huyenQuanDTO) throws URISyntaxException {
        log.debug("REST request to save HuyenQuan : {}", huyenQuanDTO);
        if (huyenQuanDTO.getId() != null) {
            throw new BadRequestAlertException("A new huyenQuan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HuyenQuanDTO result = huyenQuanService.save(huyenQuanDTO);
        return ResponseEntity
            .created(new URI("/api/huyen-quans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /huyen-quans/:id} : Updates an existing huyenQuan.
     *
     * @param id the id of the huyenQuanDTO to save.
     * @param huyenQuanDTO the huyenQuanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated huyenQuanDTO,
     * or with status {@code 400 (Bad Request)} if the huyenQuanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the huyenQuanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/huyen-quans/{id}")
    public ResponseEntity<HuyenQuanDTO> updateHuyenQuan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HuyenQuanDTO huyenQuanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HuyenQuan : {}, {}", id, huyenQuanDTO);
        if (huyenQuanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, huyenQuanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!huyenQuanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HuyenQuanDTO result = huyenQuanService.save(huyenQuanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, huyenQuanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /huyen-quans/:id} : Partial updates given fields of an existing huyenQuan, field will ignore if it is null
     *
     * @param id the id of the huyenQuanDTO to save.
     * @param huyenQuanDTO the huyenQuanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated huyenQuanDTO,
     * or with status {@code 400 (Bad Request)} if the huyenQuanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the huyenQuanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the huyenQuanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/huyen-quans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HuyenQuanDTO> partialUpdateHuyenQuan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HuyenQuanDTO huyenQuanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HuyenQuan partially : {}, {}", id, huyenQuanDTO);
        if (huyenQuanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, huyenQuanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!huyenQuanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HuyenQuanDTO> result = huyenQuanService.partialUpdate(huyenQuanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, huyenQuanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /huyen-quans} : get all the huyenQuans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of huyenQuans in body.
     */
    @GetMapping("/huyen-quans")
    public List<HuyenQuanDTO> getAllHuyenQuans() {
        log.debug("REST request to get all HuyenQuans");
        return huyenQuanService.findAll();
    }

    /**
     * {@code GET  /huyen-quans/:id} : get the "id" huyenQuan.
     *
     * @param id the id of the huyenQuanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the huyenQuanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/huyen-quans/{id}")
    public ResponseEntity<HuyenQuanDTO> getHuyenQuan(@PathVariable Long id) {
        log.debug("REST request to get HuyenQuan : {}", id);
        Optional<HuyenQuanDTO> huyenQuanDTO = huyenQuanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(huyenQuanDTO);
    }

    /**
     * {@code DELETE  /huyen-quans/:id} : delete the "id" huyenQuan.
     *
     * @param id the id of the huyenQuanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/huyen-quans/{id}")
    public ResponseEntity<Void> deleteHuyenQuan(@PathVariable Long id) {
        log.debug("REST request to delete HuyenQuan : {}", id);
        huyenQuanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
