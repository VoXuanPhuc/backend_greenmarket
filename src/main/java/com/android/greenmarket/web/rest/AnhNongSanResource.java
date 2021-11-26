package com.android.greenmarket.web.rest;

import com.android.greenmarket.domain.NongSan;
import com.android.greenmarket.repository.AnhNongSanRepository;
import com.android.greenmarket.repository.NongSanRepository;
import com.android.greenmarket.service.AnhNongSanService;
import com.android.greenmarket.service.dto.AnhNongSanDTO;
import com.android.greenmarket.service.mapper.AnhNongSanMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.android.greenmarket.domain.AnhNongSan}.
 */
@RestController
@RequestMapping("/api")
public class AnhNongSanResource {

    private final Logger log = LoggerFactory.getLogger(AnhNongSanResource.class);

    private static final String ENTITY_NAME = "anhNongSan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnhNongSanService anhNongSanService;

    private final AnhNongSanRepository anhNongSanRepository;

    @Autowired
    NongSanRepository nongSanRepository;

    @Autowired
    AnhNongSanMapper anhNongSanMapper;

    public AnhNongSanResource(AnhNongSanService anhNongSanService, AnhNongSanRepository anhNongSanRepository) {
        this.anhNongSanService = anhNongSanService;
        this.anhNongSanRepository = anhNongSanRepository;
    }

    /**
     * {@code POST  /anh-nong-sans} : Create a new anhNongSan.
     *
     * @param anhNongSanDTO the anhNongSanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anhNongSanDTO, or with status {@code 400 (Bad Request)} if the anhNongSan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anh-nong-sans")
    public ResponseEntity<AnhNongSanDTO> createAnhNongSan(@Valid @RequestBody AnhNongSanDTO anhNongSanDTO) throws URISyntaxException {
        log.debug("REST request to save AnhNongSan : {}", anhNongSanDTO);
        if (anhNongSanDTO.getId() != null) {
            throw new BadRequestAlertException("A new anhNongSan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnhNongSanDTO result = anhNongSanService.save(anhNongSanDTO);
        return ResponseEntity
            .created(new URI("/api/anh-nong-sans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anh-nong-sans/:id} : Updates an existing anhNongSan.
     *
     * @param id the id of the anhNongSanDTO to save.
     * @param anhNongSanDTO the anhNongSanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anhNongSanDTO,
     * or with status {@code 400 (Bad Request)} if the anhNongSanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anhNongSanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anh-nong-sans/{id}")
    public ResponseEntity<AnhNongSanDTO> updateAnhNongSan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnhNongSanDTO anhNongSanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnhNongSan : {}, {}", id, anhNongSanDTO);
        if (anhNongSanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anhNongSanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anhNongSanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnhNongSanDTO result = anhNongSanService.save(anhNongSanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anhNongSanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /anh-nong-sans/:id} : Partial updates given fields of an existing anhNongSan, field will ignore if it is null
     *
     * @param id the id of the anhNongSanDTO to save.
     * @param anhNongSanDTO the anhNongSanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anhNongSanDTO,
     * or with status {@code 400 (Bad Request)} if the anhNongSanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anhNongSanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anhNongSanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/anh-nong-sans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnhNongSanDTO> partialUpdateAnhNongSan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnhNongSanDTO anhNongSanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnhNongSan partially : {}, {}", id, anhNongSanDTO);
        if (anhNongSanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anhNongSanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anhNongSanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnhNongSanDTO> result = anhNongSanService.partialUpdate(anhNongSanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anhNongSanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anh-nong-sans} : get all the anhNongSans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anhNongSans in body.
     */
    @GetMapping("/anh-nong-sans")
    public List<AnhNongSanDTO> getAllAnhNongSans() {
        log.debug("REST request to get all AnhNongSans");
        return anhNongSanService.findAll();
    }

    /**
     * {@code GET  /anh-nong-sans/:id} : get the "id" anhNongSan.
     *
     * @param id the id of the anhNongSanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anhNongSanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anh-nong-sans/{id}")
    public ResponseEntity<AnhNongSanDTO> getAnhNongSan(@PathVariable Long id) {
        log.debug("REST request to get AnhNongSan : {}", id);
        Optional<AnhNongSanDTO> anhNongSanDTO = anhNongSanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anhNongSanDTO);
    }

    /**
     * {@code DELETE  /anh-nong-sans/:id} : delete the "id" anhNongSan.
     *
     * @param id the id of the anhNongSanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anh-nong-sans/{id}")
    public ResponseEntity<Void> deleteAnhNongSan(@PathVariable Long id) {
        log.debug("REST request to delete AnhNongSan : {}", id);
        anhNongSanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/get-anh-nong-sans-by-Nongsan/{idNongSan}")
    public List<AnhNongSanDTO> getAnhNsByNongSan(@PathVariable Long idNongSan) {
        NongSan ns = nongSanRepository.findById(idNongSan).get();
        return anhNongSanMapper.toDto(anhNongSanRepository.findByAnhnongsan(ns));
    }
}
