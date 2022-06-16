package de.thb.jee.authexample.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
public class BookmarkTransferEntity {

    private int roleId;
    private int userId;
    private int secondId;

}
