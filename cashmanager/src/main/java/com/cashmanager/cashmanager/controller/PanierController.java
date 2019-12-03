package com.cashmanager.cashmanager.controller;

import com.cashmanager.cashmanager.exception.ResourceNotFoundException;
import com.cashmanager.cashmanager.model.Article;
import com.cashmanager.cashmanager.model.Panier;
import com.cashmanager.cashmanager.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PanierController {

    @Autowired
    PanierRepository panierRepository;

    @GetMapping("/paniers")
    public List<Panier> getAllPaniers(){ return panierRepository.findAll();}

    @PostMapping("/paniers")
    public Panier createPanier(@Valid @RequestBody Panier panier){return panierRepository.save(panier);}

    @GetMapping("/paniers/{id}")
    public Panier updatePanier(@PathVariable(value = "id") Long panierId){
        return panierRepository.findById(panierId)
                .orElseThrow(() -> new ResourceNotFoundException("Panier", "Id", panierId));
    }

    @PutMapping("/paniers/{id]")
    public Panier updatePanier(@PathVariable(value="id") Long panierId, @Valid @RequestBody Panier panierDetails){
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new ResourceNotFoundException("Article","Id",panierId));

        panier.setBoolean(panierDetails.getBoolean());
        Panier updatedPanier = panierRepository.save(panier);

        return updatedPanier;

    }

    @DeleteMapping("/paniers/{id}")
    public ResponseEntity<?> deletePaniers(@PathVariable(value = "id") Long panierId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", panierId));

        panierRepository.delete(panier);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/paniers/article")
    public ArticleController.ArticleResponse addArticleToCart(@Valid @RequestBody String mySerializedArticle) {
        Panier panier = panierRepository.findById((long) 1)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", 1));

        Article myArticle = ArticleController.deserializedArticle(mySerializedArticle);
        panier.logArticles();
        if (panier.setArticle(myArticle)) { return new ArticleController.ArticleResponse(true, "OK"); }
        else { return new ArticleController.ArticleResponse(false, "KO"); }
    }

    @DeleteMapping("/paniers/article")
    public ArticleController.ArticleResponse deleteArticleToCart(@Valid @RequestBody String mySerializedArtile) {
        Panier panier = panierRepository.findById((long) 1)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", 1));

        Article myArticle = ArticleController.deserializedArticle(mySerializedArtile);
        panier.logArticles();
        if (panier.deleteArticle(myArticle)) { return new ArticleController.ArticleResponse(true, "OK"); }
        else { return new ArticleController.ArticleResponse(false, "KO"); }
    }
}
