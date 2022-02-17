package com.safara.entities.administration.dto;

import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
@Setter
public class Photosdto {

    private int sfidkey;
    private String image;
    private String alt;
    private Entitiesdto entity;
    private int position;
    private MultipartFile file;
}
