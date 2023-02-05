package kz.duman.bulletin_board.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.duman.bulletin_board.dto.BulletinBoardDTO;
import kz.duman.bulletin_board.payload.NewBoardRequest;
import kz.duman.bulletin_board.security.jwt.SecurityUser;
import kz.duman.bulletin_board.service.BulletinBoardCorn;
import kz.duman.bulletin_board.service.BulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "API доска объявлений", description = "api для доски объявлений")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/ads")
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;
    private final BulletinBoardCorn bulletinBoardCorn;

    @ApiOperation("Создания объявления и размещение его на доске объявлений")
    @PostMapping
    public ResponseEntity<String> addNewBoard(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody NewBoardRequest request
    ) {
        bulletinBoardService.addNewBoard(request, securityUser.getId());
        return ResponseEntity.ok("Ads successfully created");
    }

    @ApiOperation("Получить весь список обявлений")
    @GetMapping
    public ResponseEntity<List<BulletinBoardDTO>> getAllAbs() {
        var boardDTOS = bulletinBoardService.getAllAbs()
                .stream().map(BulletinBoardDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(boardDTOS);
    }

    @ApiOperation("Фильтр по названию обявлений")
    @GetMapping("/filer")
    public ResponseEntity<List<BulletinBoardDTO>> findByNameContaining(
            @RequestParam("name") String name
    ) {
        var boardDTOS = bulletinBoardService.findByNameContaining(name)
                .stream().map(BulletinBoardDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(boardDTOS);
    }

    @ApiOperation("Запрос на покупку товара, предложение цены, участие в аукционе")
    @PutMapping("/request")
    public ResponseEntity<BulletinBoardDTO> requestPurchase(
            @RequestParam Long boardId,
            @RequestParam Long minPrice,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        var bulletinBoard = bulletinBoardService.requestPurchase(boardId, securityUser.getId(), minPrice);
        return ResponseEntity.ok(BulletinBoardDTO.from(bulletinBoard));
    }

}
