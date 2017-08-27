-- -----------------------------------------------------
-- Table `Cassebot_Test`.`Usuario`
-- -----------------------------------------------------
CREATE SEQUENCE  Usuario_seq;

CREATE TABLE IF NOT EXISTS  Usuario (
  usu_id INT NOT NULL DEFAULT NEXTVAL (' Usuario_seq'),
  usu_apelido VARCHAR(100) NOT NULL,
  usu_liberado INT NOT NULL DEFAULT 1 ,
  usu_id_bot VARCHAR(45) NOT NULL,
  PRIMARY KEY (usu_id))
;


-- -----------------------------------------------------
-- Table `Cassebot_Test`.`Rota`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  Rota (
  rot_nome VARCHAR(200) NOT NULL,
  rot_grupo VARCHAR(200) NOT NULL,
  rot_basico INT NOT NULL DEFAULT 0,
  rot_admin INT NOT NULL DEFAULT 0,
  rot_invisivel INT NOT NULL DEFAULT 0,
  PRIMARY KEY (rot_nome, rot_grupo))
;


-- -----------------------------------------------------
-- Table `Cassebot_Test`.`usuario_rota`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  usuario_rota (
  rot_nome VARCHAR(200) NOT NULL,
  rot_grupo VARCHAR(200) NOT NULL,
  usu_id INT NOT NULL,
  PRIMARY KEY (rot_nome, rot_grupo, usu_id)
 ,
  CONSTRAINT fk_Pessoa_has_Rotas_Rotas1
    FOREIGN KEY (rot_nome , rot_grupo)
    REFERENCES  Rota (rot_nome , rot_grupo)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_usuario_rota_Usuario1
    FOREIGN KEY (usu_id)
    REFERENCES  Usuario (usu_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE INDEX fk_Pessoa_has_Rotas_Rotas1_idx ON  usuario_rota (rot_nome ASC, rot_grupo ASC);
CREATE INDEX fk_usuario_rota_Usuario1_idx ON  usuario_rota (usu_id ASC);


-- -----------------------------------------------------
-- Table `Cassebot_Test`.`MensagemLog`
-- -----------------------------------------------------
CREATE SEQUENCE  MensagemLog_seq;

CREATE TABLE IF NOT EXISTS  MensagemLog (
  menlog_id INT NOT NULL DEFAULT NEXTVAL (' MensagemLog_seq'),
  menlog_mensagem VARCHAR(1000) NOT NULL,
  menlog_data TIMESTAMP(0) NOT NULL,
  menlog_origem VARCHAR(100) NOT NULL,
  usu_id INT NOT NULL,
  PRIMARY KEY (menlog_id)
 ,
  CONSTRAINT fk_MensagemLog_Usuario1
    FOREIGN KEY (usu_id)
    REFERENCES  Usuario (usu_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE INDEX fk_MensagemLog_Usuario1_idx ON  MensagemLog (usu_id ASC);


-- -----------------------------------------------------
-- Table `Cassebot_Test`.`Tabela`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  Tabela (
  tabela VARCHAR(200) NOT NULL,
  campo VARCHAR(200) NOT NULL,
  chave INT NOT NULL,
  unico INT NOT NULL DEFAULT 0 ,
  conteudo_txt VARCHAR(500) NULL,
  conteudo_int INT NULL,
  conteudo_double DOUBLE PRECISION NULL,
  conteudo_data TIMESTAMP(0) NULL,
  conteudo_boolean VARCHAR(1) NULL,
  PRIMARY KEY (tabela, campo, chave))
 ;