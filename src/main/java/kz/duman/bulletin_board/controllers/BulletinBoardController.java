package kz.duman.bulletin_board.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.duman.bulletin_board.dto.BulletinBoardDTO;
import kz.duman.bulletin_board.payload.NewBoardRequest;
import kz.duman.bulletin_board.security.jwt.SecurityUser;
import kz.duman.bulletin_board.service.BulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Bulletin board API", description = "api for ads")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/ads")
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;

    @ApiOperation("Create an ad and post it on the bulletin board")
    @PostMapping
    public ResponseEntity<String> addNewBoard(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody NewBoardRequest request
    ) {
        bulletinBoardService.addNewBoard(request, securityUser.getId());
        return ResponseEntity.ok("Ads successfully created");
    }

    @ApiOperation("Get a list of ads")
    @GetMapping
    public ResponseEntity<List<BulletinBoardDTO>> getAllAbs() {
        var boardDTOS = bulletinBoardService.getAllAbs()
                .stream().map(BulletinBoardDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(boardDTOS);
    }

    @ApiOperation("find a abs by name")
    @GetMapping("/find")
    public ResponseEntity<List<BulletinBoardDTO>> findByNameContaining(
            @RequestParam("name") String name
    ) {
        var boardDTOS = bulletinBoardService.findByNameContaining(name)
                .stream().map(BulletinBoardDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(boardDTOS);
    }

    @ApiOperation("Request for the purchase of goods")
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
