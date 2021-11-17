package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.XaPhuongRepository;
import com.android.greenmarket.service.XaPhuongService;
import com.android.greenmarket.service.dto.XaPhuongDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.XaPhuong}.
 */
@RestController
@RequestMapping("/api")
public class XaPhuongResource {

    private final Logger log = LoggerFactory.getLogger(XaPhuongResource.class);

    private static final String ENTITY_NAME = "xaPhuong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final XaPhuongService xaPhuongService;

    private final XaPhuongRepository xaPhuongRepository;

    public XaPhuongResource(XaPhuongService xaPhuongService, XaPhuongRepository xaPhuongRepository) {
        this.xaPhuongService = xaPhuongService;
        this.xaPhuongRepository = xaPhuongRepository;
    }

    /**
     * {@code POST  /xa-phuongs} : Create a new xaPhuong.
     *
     * @param xaPhuongDTO the xaPhuongDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new xaPhuongDTO, or with status {@code 400 (Bad Request)} if the xaPhuong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/xa-phuongs")
    public ResponseEntity<XaPhuongDTO> createXaPhuong(@RequestBody XaPhuongDTO xaPhuongDTO) throws URISyntaxException {
        log.debug("REST request to save XaPhuong : {}", xaPhuongDTO);
        if (xaPhuongDTO.getId() != null) {
            throw new BadRequestAlertException("A new xaPhuong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        XaPhuongDTO result = xaPhuongService.save(xaPhuongDTO);
        return ResponseEntity
            .created(new URI("/api/xa-phuongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /xa-phuongs/:id} : Updates an existing xaPhuong.
     *
     * @param id the id of the xaPhuongDTO to save.
     * @param xaPhuongDTO the xaPhuongDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated xaPhuongDTO,
     * or with status {@code 400 (Bad Request)} if the xaPhuongDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the xaPhuongDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/xa-phuongs/{id}")
    public ResponseEntity<XaPhuongDTO> updateXaPhuong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody XaPhuongDTO xaPhuongDTO
    ) throws URISyntaxException {
        log.debug("REST request to update XaPhuong : {}, {}", id, xaPhuongDTO);
        if (xaPhuongDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, xaPhuongDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!xaPhuongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        XaPhuongDTO result = xaPhuongService.save(xaPhuongDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, xaPhuongDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /xa-phuongs/:id} : Partial updates given fields of an existing xaPhuong, field will ignore if it is null
     *
     * @param id the id of the xaPhuongDTO to save.
     * @param xaPhuongDTO the xaPhuongDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated xaPhuongDTO,
     * or with status {@code 400 (Bad Request)} if the xaPhuongDTO is not valid,
     * or with status {@code 404 (Not Found)} if the xaPhuongDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the xaPhuongDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/xa-phuongs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<XaPhuongDTO> partialUpdateXaPhuong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody XaPhuongDTO xaPhuongDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update XaPhuong partially : {}, {}", id, xaPhuongDTO);
        if (xaPhuongDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, xaPhuongDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!xaPhuongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<XaPhuongDTO> result = xaPhuongService.partialUpdate(xaPhuongDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, xaPhuongDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /xa-phuongs} : get all the xaPhuongs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of xaPhuongs in body.
     */
    @GetMapping("/xa-phuongs")
    public List<XaPhuongDTO> getAllXaPhuongs() {
        log.debug("REST request to get all XaPhuongs");
        return xaPhuongService.findAll();
    }

    /**
     * {@code GET  /xa-phuongs/:id} : get the "id" xaPhuong.
     *
     * @param id the id of the xaPhuongDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the xaPhuongDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/xa-phuongs/{id}")
    public ResponseEntity<XaPhuongDTO> getXaPhuong(@PathVariable Long id) {
        log.debug("REST request to get XaPhuong : {}", id);
        Optional<XaPhuongDTO> xaPhuongDTO = xaPhuongService.findOne(id);
        return ResponseUtil.wrapOrNotFound(xaPhuongDTO);
    }

    /**
     * {@code DELETE  /xa-phuongs/:id} : delete the "id" xaPhuong.
     *
     * @param id the id of the xaPhuongDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/xa-phuongs/{id}")
    public ResponseEntity<Void> deleteXaPhuong(@PathVariable Long id) {
        log.debug("REST request to delete XaPhuong : {}", id);
        xaPhuongService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
