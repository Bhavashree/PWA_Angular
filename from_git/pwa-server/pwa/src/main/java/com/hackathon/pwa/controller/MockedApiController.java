package com.hackathon.pwa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.pwa.model.Aircraft;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockedApiController {

  private ObjectMapper objectMapper;

  @Value("classpath:json/aircraft.json")
  Resource mockedBoeing737;

  @Autowired
  public MockedApiController(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @CrossOrigin(origins = "*")
  @RequestMapping("/aircrafts")
  public ResponseEntity<List<Aircraft>> getAll() {

    try {
      return new ResponseEntity<List<Aircraft>>(
          objectMapper.readValue(mockedBoeing737.getInputStream(), ArrayList.class),
          HttpStatus.OK
      );
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private byte[] convertToByteArray() throws IOException {
    // open image
    BufferedImage bufferedImage = ImageIO.read(mockedBoeing737.getFile());

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "jpg", bos);
    return bos.toByteArray();
  }

}
