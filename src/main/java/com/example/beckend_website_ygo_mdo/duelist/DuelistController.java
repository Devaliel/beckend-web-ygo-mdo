package com.example.beckend_website_ygo_mdo.duelist;

import com.example.beckend_website_ygo_mdo.duelist.DTO.DuelistMatchHistoryDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/duelists")
public class DuelistController {

    private final DuelistService duelistService;

    public DuelistController(DuelistService duelistService) {
        this.duelistService = duelistService;
    }

    // CREATE single duelist
    @PostMapping("/register")
    public Duelist registerDuelist(@RequestBody Duelist duelist) {
        return duelistService.registerDuelist(duelist);
    }

    // CREATE multiple duelists (bulk import)
    @PostMapping("/import")
    public List<Duelist> importDuelists(@RequestBody List<Duelist> duelists) {
        return duelistService.saveAllDuelists(duelists);
    }

    // READ ALL
    @GetMapping
    public List<Duelist> getAllDuelists() {
        System.out.println(">>> getAllDuelists called!"); // debug log
        return duelistService.getAllDuelists();
    }

    // READ ONE (by numeric ID)
    @GetMapping("/{id:\\d+}")
    public Duelist getDuelistById(@PathVariable Long id) {
        return duelistService.getDuelistById(id);
    }

    @GetMapping("/{id}/matches")
    public List<DuelistMatchHistoryDTO> getDuelistMatchHistory(@PathVariable Long id) {
        return duelistService.getDuelistMatchHistory(id);
    }


}
