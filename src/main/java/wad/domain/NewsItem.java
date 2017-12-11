/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Tomi
 */
@AllArgsConstructor
@Data
@Entity
public class NewsItem extends AbstractPersistable<Long> {
    
    private String headline;
    private String lead;
    private String text;
    @Lob
    private byte[] pic;
    private LocalDateTime pubTime;
    private LocalDate pubDate;
    private int viewCount;
    
    @ManyToMany
    private List<Writer> writers;
    
    @ManyToMany
    private List<Category> categories;
    
    public NewsItem() {
        this.viewCount = 0;
        this.pubTime = LocalDateTime.now();
        this.pubDate = LocalDate.now();
    }
}
