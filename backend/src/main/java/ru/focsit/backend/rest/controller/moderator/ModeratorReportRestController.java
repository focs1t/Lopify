package ru.focsit.backend.rest.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.service.SongService;

import java.util.List;

@RestController
@RequestMapping("/moderator/reports")
@RequiredArgsConstructor
public class ModeratorReportRestController {

    private final SongService songService;

    @GetMapping("/top10-songs")
    public List<SongDto> getTop10SongsByFavorites() {
        return songService.getTop10SongsByFavorites();
    }
}