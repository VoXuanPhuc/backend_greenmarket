package com.android.greenmarket.web.rest;

import com.android.greenmarket.repository.HoaDonRepository;
import com.android.greenmarket.service.HoaDonService;
import com.android.greenmarket.service.dto.HoaDonDTO;
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
 * REST controller for managing {@link com.android.greenmarket.domain.HoaDon}.
 */
@RestController
@RequestMapping("/api")
public class HoaDonResource {

    private final Logger log = LoggerFactory.getLogger(HoaDonResource.class);

    private static final String ENTITY_NAME = "hoaDon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoaDonService hoaDonService;

    private final HoaDonRepository hoaDonRepository;

    public HoaDonResource(HoaDonService hoaDonService, HoaDonRepository hoaDonRepository) {
        this.hoaDonService = hoaDonService;
        this.hoaDonRepository = hoaDonRepository;
    }

    /**
     * {@code POST  /hoa-dons} : Create a new hoaDon.
     *
     * @param hoaDonDTO the hoaDonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hoaDonDTO, or with status {@code 400 (Bad Request)} if the hoaDon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hoa-dons")
    public ResponseEntity<HoaDonDTO> createHoaDon(@Valid @RequestBody HoaDonDTO hoaDonDTO) throws URISyntaxException {
        log.debug("REST request to save HoaDon : {}", hoaDonDTO);
        if (hoaDonDTO.getId() != null) {
            throw new BadRequestAlertException("A new hoaDon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoaDonDTO result = hoaDonService.save(hoaDonDTO);
        return ResponseEntity
            .created(new URI("/api/hoa-dons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hoa-dons/:id} : Updates an existing hoaDon.
     *
     * @param id the id of the hoaDonDTO to save.
     * @param hoaDonDTO the hoaDonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoaDonDTO,
     * or with status {@code 400 (Bad Request)} if the hoaDonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hoaDonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hoa-dons/{id}")
    public ResponseEntity<HoaDonDTO> updateHoaDon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HoaDonDTO hoaDonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HoaDon : {}, {}", id, hoaDonDTO);
        if (hoaDonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoaDonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoaDonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoaDonDTO result = hoaDonService.save(hoaDonDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoaDonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hoa-dons/:id} : Partial updates given fields of an existing hoaDon, field will ignore if it is null
     *
     * @param id the id of the hoaDonDTO to save.
     * @param hoaDonDTO the hoaDonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoaDonDTO,
     * or with status {@code 400 (Bad Request)} if the hoaDonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hoaDonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hoaDonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hoa-dons/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HoaDonDTO> partialUpdateHoaDon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HoaDonDTO hoaDonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HoaDon partially : {}, {}", id, hoaDonDTO);
        if (hoaDonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoaDonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoaDonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoaDonDTO> result = hoaDonService.partialUpdate(hoaDonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoaDonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hoa-dons} : get all the hoaDons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hoaDons in body.
     */
    @GetMapping("/hoa-dons")
    public List<HoaDonDTO> getAllHoaDons() {
        log.debug("REST request to get all HoaDons");
        return hoaDonService.findAll();
    }

    /**
     * {@code GET  /hoa-dons/:id} : get the "id" hoaDon.
     *
     * @param id the id of the hoaDonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hoaDonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hoa-dons/{id}")
    public ResponseEntity<HoaDonDTO> getHoaDon(@PathVariable Long id) {
        log.debug("REST request to get HoaDon : {}", id);
        Optional<HoaDonDTO> hoaDonDTO = hoaDonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hoaDonDTO);
    }

    /**
     * {@code DELETE  /hoa-dons/:id} : delete the "id" hoaDon.
     *
     * @param id the id of the hoaDonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hoa-dons/{id}")
    public ResponseEntity<Void> deleteHoaDon(@PathVariable Long id) {
        log.debug("REST request to delete HoaDon : {}", id);
        hoaDonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
