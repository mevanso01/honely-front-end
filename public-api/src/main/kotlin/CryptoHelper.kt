package com.metarhythm.honely.publicapi

import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class CryptoHelper {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun publicKey(): String {
    return read("public_key.pem")
  }

  fun privateKey(): String {
    return read("private_key.pem")
  }

  private fun read(file: String): String {
    val env = System.getenv()
    val pemPath = env.getOrDefault("PEM_PATH", "pem_keys")
    var path = Paths.get(pemPath, file)
    logger.info("Path: {}", path.toAbsolutePath())
    logger.info("Exists?: {}", path.toFile().exists())
    if (!path.toFile().exists()) {
      path = Paths.get("..", "auth-api", file)
    }
    return java.lang.String.join("\n", Files.readAllLines(path, StandardCharsets.UTF_8))
  }

}
