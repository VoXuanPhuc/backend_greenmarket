package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.ChiTietHoaDonRepository;
import com.android.greenmarket.service.ChiTietHoaDonService;
import com.android.greenmarket.service.dto.ChiTietHoaDonDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.ChiTietHoaDon}.
 */
@RestController
@RequestMapping("/api")
public class ChiTietHoaDonResource {

    private final Logger log = LoggerFactory.getLogger(ChiTietHoaDonResource.class);

    private static final String ENTITY_NAME = "chiTietHoaDon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChiTietHoaDonService chiTietHoaDonService;

    private final ChiTietHoaDonRepository chiTietHoaDonRepository;

    public ChiTietHoaDonResource(ChiTietHoaDonService chiTietHoaDonService, ChiTietHoaDonRepository chiTietHoaDonRepository) {
        this.chiTietHoaDonService = chiTietHoaDonService;
        this.chiTietHoaDonRepository = chiTietHoaDonRepository;
    }

    /**
     * {@code POST  /chi-tiet-hoa-dons} : Create a new chiTietHoaDon.
     *
     * @param chiTietHoaDonDTO the chiTietHoaDonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chiTietHoaDonDTO, or with status {@code 400 (Bad Request)} if the chiTietHoaDon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chi-tiet-hoa-dons")
    public ResponseEntity<ChiTietHoaDonDTO> createChiTietHoaDon(@Valid @RequestBody ChiTietHoaDonDTO chiTietHoaDonDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChiTietHoaDon : {}", chiTietHoaDonDTO);
        if (chiTietHoaDonDTO.getId() != null) {
            throw new BadRequestAlertException("A new chiTietHoaDon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChiTietHoaDonDTO result = chiTietHoaDonService.save(chiTietHoaDonDTO);
        return ResponseEntity
            .created(new URI("/api/chi-tiet-hoa-dons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chi-tiet-hoa-dons/:id} : Updates an existing chiTietHoaDon.
     *
     * @param id the id of the chiTietHoaDonDTO to save.
     * @param chiTietHoaDonDTO the chiTietHoaDonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietHoaDonDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietHoaDonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chiTietHoaDonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chi-tiet-hoa-dons/{id}")
    public ResponseEntity<ChiTietHoaDonDTO> updateChiTietHoaDon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChiTietHoaDonDTO chiTietHoaDonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChiTietHoaDon : {}, {}", id, chiTietHoaDonDTO);
        if (chiTietHoaDonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietHoaDonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietHoaDonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChiTietHoaDonDTO result = chiTietHoaDonService.save(chiTietHoaDonDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chiTietHoaDonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chi-tiet-hoa-dons/:id} : Partial updates given fields of an existing chiTietHoaDon, field will ignore if it is null
     *
     * @param id the id of the chiTietHoaDonDTO to save.
     * @param chiTietHoaDonDTO the chiTietHoaDonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietHoaDonDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietHoaDonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chiTietHoaDonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chiTietHoaDonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chi-tiet-hoa-dons/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ChiTietHoaDonDTO> partialUpdateChiTietHoaDon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChiTietHoaDonDTO chiTietHoaDonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChiTietHoaDon partially : {}, {}", id, chiTietHoaDonDTO);
        if (chiTietHoaDonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietHoaDonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietHoaDonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChiTietHoaDonDTO> result = chiTietHoaDonService.partialUpdate(chiTietHoaDonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chiTietHoaDonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chi-tiet-hoa-dons} : get all the chiTietHoaDons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chiTietHoaDons in body.
     */
    @GetMapping("/chi-tiet-hoa-dons")
    public List<ChiTietHoaDonDTO> getAllChiTietHoaDons() {
        log.debug("REST request to get all ChiTietHoaDons");
        return chiTietHoaDonService.findAll();
    }

    /**
     * {@code GET  /chi-tiet-hoa-dons/:id} : get the "id" chiTietHoaDon.
     *
     * @param id the id of the chiTietHoaDonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chiTietHoaDonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chi-tiet-hoa-dons/{id}")
    public ResponseEntity<ChiTietHoaDonDTO> getChiTietHoaDon(@PathVariable Long id) {
        log.debug("REST request to get ChiTietHoaDon : {}", id);
        Optional<ChiTietHoaDonDTO> chiTietHoaDonDTO = chiTietHoaDonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chiTietHoaDonDTO);
    }

    /**
     * {@code DELETE  /chi-tiet-hoa-dons/:id} : delete the "id" chiTietHoaDon.
     *
     * @param id the id of the chiTietHoaDonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chi-tiet-hoa-dons/{id}")
    public ResponseEntity<Void> deleteChiTietHoaDon(@PathVariable Long id) {
        log.debug("REST request to delete ChiTietHoaDon : {}", id);
        chiTietHoaDonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
