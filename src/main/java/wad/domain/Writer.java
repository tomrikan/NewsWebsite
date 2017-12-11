/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Tomi
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Writer extends AbstractPersistable<Long> {
    
    private String name;
    
    @ManyToMany(mappedBy = "writers")
    private List<NewsItem> writtenNews;
    
}
