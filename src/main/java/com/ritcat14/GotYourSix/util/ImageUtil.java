package com.ritcat14.GotYourSix.util;

import com.ritcat14.GotYourSix.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;



public class ImageUtil {

  /**
   * Returns an image resource.
   *
   * @param filename the filename of the image to load
   * @return the loaded image
   */
  public static BufferedImage getImage(String filename) {
    URL url = Game.class.getResource(filename);

    if (url == null) {
      return null;
    }

    try {
      final BufferedImage result = ImageIO.read(url);

      if (result == null) {}
      return result;
    } catch (IOException e) {
      return null;
    }

  }




}
