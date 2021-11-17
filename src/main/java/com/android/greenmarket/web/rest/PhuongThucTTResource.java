package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.PhuongThucTTRepository;
import com.android.greenmarket.service.PhuongThucTTService;
import com.android.greenmarket.service.dto.PhuongThucTTDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.PhuongThucTT}.
 */
@RestController
@RequestMapping("/api")
public class PhuongThucTTResource {

    private final Logger log = LoggerFactory.getLogger(PhuongThucTTResource.class);

    private static final String ENTITY_NAME = "phuongThucTT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhuongThucTTService phuongThucTTService;

    private final PhuongThucTTRepository phuongThucTTRepository;

    public PhuongThucTTResource(PhuongThucTTService phuongThucTTService, PhuongThucTTRepository phuongThucTTRepository) {
        this.phuongThucTTService = phuongThucTTService;
        this.phuongThucTTRepository = phuongThucTTRepository;
    }

    /**
     * {@code POST  /phuong-thuc-tts} : Create a new phuongThucTT.
     *
     * @param phuongThucTTDTO the phuongThucTTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phuongThucTTDTO, or with status {@code 400 (Bad Request)} if the phuongThucTT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phuong-thuc-tts")
    public ResponseEntity<PhuongThucTTDTO> createPhuongThucTT(@Valid @RequestBody PhuongThucTTDTO phuongThucTTDTO)
        throws URISyntaxException {
        log.debug("REST request to save PhuongThucTT : {}", phuongThucTTDTO);
        if (phuongThucTTDTO.getId() != null) {
            throw new BadRequestAlertException("A new phuongThucTT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhuongThucTTDTO result = phuongThucTTService.save(phuongThucTTDTO);
        return ResponseEntity
            .created(new URI("/api/phuong-thuc-tts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phuong-thuc-tts/:id} : Updates an existing phuongThucTT.
     *
     * @param id the id of the phuongThucTTDTO to save.
     * @param phuongThucTTDTO the phuongThucTTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phuongThucTTDTO,
     * or with status {@code 400 (Bad Request)} if the phuongThucTTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phuongThucTTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phuong-thuc-tts/{id}")
    public ResponseEntity<PhuongThucTTDTO> updatePhuongThucTT(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PhuongThucTTDTO phuongThucTTDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PhuongThucTT : {}, {}", id, phuongThucTTDTO);
        if (phuongThucTTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phuongThucTTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phuongThucTTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhuongThucTTDTO result = phuongThucTTService.save(phuongThucTTDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, phuongThucTTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phuong-thuc-tts/:id} : Partial updates given fields of an existing phuongThucTT, field will ignore if it is null
     *
     * @param id the id of the phuongThucTTDTO to save.
     * @param phuongThucTTDTO the phuongThucTTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phuongThucTTDTO,
     * or with status {@code 400 (Bad Request)} if the phuongThucTTDTO is not valid,
     * or with status {@code 404 (Not Found)} if the phuongThucTTDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the phuongThucTTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phuong-thuc-tts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PhuongThucTTDTO> partialUpdatePhuongThucTT(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PhuongThucTTDTO phuongThucTTDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhuongThucTT partially : {}, {}", id, phuongThucTTDTO);
        if (phuongThucTTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phuongThucTTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phuongThucTTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhuongThucTTDTO> result = phuongThucTTService.partialUpdate(phuongThucTTDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, phuongThucTTDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /phuong-thuc-tts} : get all the phuongThucTTS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phuongThucTTS in body.
     */
    @GetMapping("/phuong-thuc-tts")
    public List<PhuongThucTTDTO> getAllPhuongThucTTS() {
        log.debug("REST request to get all PhuongThucTTS");
        return phuongThucTTService.findAll();
    }

    /**
     * {@code GET  /phuong-thuc-tts/:id} : get the "id" phuongThucTT.
     *
     * @param id the id of the phuongThucTTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phuongThucTTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phuong-thuc-tts/{id}")
    public ResponseEntity<PhuongThucTTDTO> getPhuongThucTT(@PathVariable Long id) {
        log.debug("REST request to get PhuongThucTT : {}", id);
        Optional<PhuongThucTTDTO> phuongThucTTDTO = phuongThucTTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phuongThucTTDTO);
    }

    /**
     * {@code DELETE  /phuong-thuc-tts/:id} : delete the "id" phuongThucTT.
     *
     * @param id the id of the phuongThucTTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phuong-thuc-tts/{id}")
    public ResponseEntity<Void> deletePhuongThucTT(@PathVariable Long id) {
        log.debug("REST request to delete PhuongThucTT : {}", id);
        phuongThucTTService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
