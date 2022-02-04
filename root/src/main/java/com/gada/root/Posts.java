package com.gada.root;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
// import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
// import javax.persistence.Transient;
// import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.NotEmpty;
// import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// import org.springframework.format.annotation.NumberFormat;
// import org.springframework.web.multipart.MultipartFile;
import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] ProductImg;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String Base64Img;

    @NotNull
    @NotBlank(message = "*Description is required!")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    @NotNull
    @NotBlank(message = "*Title is required!")
    private String title;

    @NotNull(message = "*Account Number Cannot be null!")
    @Min(value = 999, message = "*Account Number is invalid!")
    private Long accountNumber;
    private LocalDateTime date;

    @NotBlank

    @NotNull(message = "*Theme or or brief explanation of Campaign is required")
    @Size(min=40)

    private String theme;

    @NotNull(message = "*Goal is required")
    private Long goal;

    private float totalAmount;
     
    
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;


    @OneToMany(mappedBy = "post")
    private List<Donation> donations;

}
