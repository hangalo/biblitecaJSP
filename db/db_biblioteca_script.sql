-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.17


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema biblioteca_db
--

CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

--
-- Definition of table `autor`
--

DROP TABLE IF EXISTS `autor`;
CREATE TABLE `autor` (
  `id_autor` int(11) NOT NULL AUTO_INCREMENT,
  `nome_autor` varchar(45) DEFAULT NULL,
  `sobrenome_autor` varchar(45) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `breve_biografia` varchar(400) DEFAULT NULL,
  `id_municipio` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_autor`),
  KEY `fk_autor_municipio1_idx` (`id_municipio`),
  CONSTRAINT `fk_autor_municipio1` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`id_municipio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `autor`
--

/*!40000 ALTER TABLE `autor` DISABLE KEYS */;
INSERT INTO `autor` (`id_autor`,`nome_autor`,`sobrenome_autor`,`data_nascimento`,`breve_biografia`,`id_municipio`) VALUES 
 (1,'Agostinho','Neto','2016-03-05','Biografia',1);
/*!40000 ALTER TABLE `autor` ENABLE KEYS */;


--
-- Definition of table `autor_livro`
--

DROP TABLE IF EXISTS `autor_livro`;
CREATE TABLE `autor_livro` (
  `livro_id_livro` int(11) NOT NULL,
  `autor_id_autor` int(11) NOT NULL,
  `ano` int(11) NOT NULL,
  PRIMARY KEY (`livro_id_livro`,`autor_id_autor`),
  KEY `fk_autor_livro_livro1_idx` (`livro_id_livro`),
  KEY `fk_autor_livro_autor1_idx` (`autor_id_autor`),
  CONSTRAINT `fk_autor_livro_autor1` FOREIGN KEY (`autor_id_autor`) REFERENCES `autor` (`id_autor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_autor_livro_livro1` FOREIGN KEY (`livro_id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `autor_livro`
--

/*!40000 ALTER TABLE `autor_livro` DISABLE KEYS */;
/*!40000 ALTER TABLE `autor_livro` ENABLE KEYS */;


--
-- Definition of table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,
  `nome_categoria` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categoria`
--

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;


--
-- Definition of table `editora`
--

DROP TABLE IF EXISTS `editora`;
CREATE TABLE `editora` (
  `id_editora` int(11) NOT NULL AUTO_INCREMENT,
  `nome_editora` varchar(100) DEFAULT NULL,
  `casa_editora` varchar(45) DEFAULT NULL,
  `rua_editora` varchar(45) DEFAULT NULL,
  `bairro_editora` varchar(45) DEFAULT NULL,
  `caixa_postal_editora` varchar(45) DEFAULT NULL,
  `telefone_fixo_editora` varchar(45) DEFAULT NULL,
  `telefone_movel_editora` varchar(45) DEFAULT NULL,
  `email_editora` varchar(45) DEFAULT NULL,
  `home_page_editora` varchar(45) DEFAULT NULL,
  `id_municipio` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_editora`),
  KEY `fk_editora_municipio1_idx` (`id_municipio`),
  CONSTRAINT `fk_editora_municipio1` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`id_municipio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `editora`
--

/*!40000 ALTER TABLE `editora` DISABLE KEYS */;
INSERT INTO `editora` (`id_editora`,`nome_editora`,`casa_editora`,`rua_editora`,`bairro_editora`,`caixa_postal_editora`,`telefone_fixo_editora`,`telefone_movel_editora`,`email_editora`,`home_page_editora`,`id_municipio`) VALUES 
 (1,'','','',NULL,'','','','','',1);
/*!40000 ALTER TABLE `editora` ENABLE KEYS */;


--
-- Definition of table `emprestimo`
--

DROP TABLE IF EXISTS `emprestimo`;
CREATE TABLE `emprestimo` (
  `id_livro` int(11) NOT NULL,
  `id_leitor` int(11) NOT NULL,
  `data_emprestimo` date NOT NULL,
  `data_devolucao` date DEFAULT NULL,
  `observacoes` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_leitor`,`id_livro`,`data_emprestimo`),
  KEY `fk_emprestimo_livro1_idx` (`id_livro`),
  KEY `fk_emprestimo_leitor1_idx` (`id_leitor`),
  CONSTRAINT `fk_emprestimo_leitor1` FOREIGN KEY (`id_leitor`) REFERENCES `leitor` (`id_leitor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emprestimo_livro1` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `emprestimo`
--

/*!40000 ALTER TABLE `emprestimo` DISABLE KEYS */;
/*!40000 ALTER TABLE `emprestimo` ENABLE KEYS */;


--
-- Definition of table `leitor`
--

DROP TABLE IF EXISTS `leitor`;
CREATE TABLE `leitor` (
  `id_leitor` int(11) NOT NULL AUTO_INCREMENT,
  `nome_leitor` varchar(45) DEFAULT NULL,
  `sobrenome_leitor` varchar(45) DEFAULT NULL,
  `data_nascimento` varchar(45) DEFAULT NULL,
  `data_inscricao` date DEFAULT NULL,
  `bairro_leitor` varchar(45) DEFAULT NULL,
  `rua_leitor` varchar(45) DEFAULT NULL,
  `casa_leitor` varchar(45) DEFAULT NULL,
  `id_municipio` int(11) NOT NULL,
  PRIMARY KEY (`id_leitor`),
  KEY `fk_leitor_municipio1_idx` (`id_municipio`),
  CONSTRAINT `fk_leitor_municipio1` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`id_municipio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `leitor`
--

/*!40000 ALTER TABLE `leitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `leitor` ENABLE KEYS */;


--
-- Definition of table `lingua`
--

DROP TABLE IF EXISTS `lingua`;
CREATE TABLE `lingua` (
  `id_lingua` int(11) NOT NULL AUTO_INCREMENT,
  `nome_lingua` varchar(45) DEFAULT NULL COMMENT 'lingua em que o livro foi escrito',
  PRIMARY KEY (`id_lingua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `lingua`
--

/*!40000 ALTER TABLE `lingua` DISABLE KEYS */;
/*!40000 ALTER TABLE `lingua` ENABLE KEYS */;


--
-- Definition of table `livro`
--

DROP TABLE IF EXISTS `livro`;
CREATE TABLE `livro` (
  `id_livro` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(100) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `data_publicacao` date DEFAULT NULL,
  `id_lingua` int(11) NOT NULL,
  `edicao` char(45) DEFAULT NULL,
  `resumo` varchar(400) DEFAULT NULL,
  `sessao` varchar(45) DEFAULT NULL,
  `estante` int(11) DEFAULT NULL,
  `posicao` int(11) DEFAULT NULL,
  `id_editora` int(11) NOT NULL,
  PRIMARY KEY (`id_livro`),
  KEY `fk_livro_editora_idx` (`id_editora`),
  KEY `fk_livro_lingua1_idx` (`id_lingua`),
  CONSTRAINT `fk_livro_editora` FOREIGN KEY (`id_editora`) REFERENCES `editora` (`id_editora`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_lingua1` FOREIGN KEY (`id_lingua`) REFERENCES `lingua` (`id_lingua`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `livro`
--

/*!40000 ALTER TABLE `livro` DISABLE KEYS */;
/*!40000 ALTER TABLE `livro` ENABLE KEYS */;


--
-- Definition of table `livro_categoria`
--

DROP TABLE IF EXISTS `livro_categoria`;
CREATE TABLE `livro_categoria` (
  `livro_id_livro` int(11) NOT NULL,
  `categoria_id_categoria` int(11) NOT NULL,
  `data_registo` date NOT NULL,
  PRIMARY KEY (`livro_id_livro`,`categoria_id_categoria`,`data_registo`),
  KEY `fk_livro_categoria_categoria1_idx` (`categoria_id_categoria`),
  KEY `fk_livro_categoria_livro1_idx` (`livro_id_livro`),
  CONSTRAINT `fk_livro_categoria_categoria1` FOREIGN KEY (`categoria_id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_categoria_livro1` FOREIGN KEY (`livro_id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `livro_categoria`
--

/*!40000 ALTER TABLE `livro_categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `livro_categoria` ENABLE KEYS */;


--
-- Definition of table `municipio`
--

DROP TABLE IF EXISTS `municipio`;
CREATE TABLE `municipio` (
  `id_municipio` int(11) NOT NULL AUTO_INCREMENT,
  `nome_municipio` varchar(45) DEFAULT NULL,
  `id_provincia` int(11) NOT NULL,
  PRIMARY KEY (`id_municipio`),
  KEY `fk_municipio_provincia1_idx` (`id_provincia`),
  CONSTRAINT `fk_municipio_provincia1` FOREIGN KEY (`id_provincia`) REFERENCES `provincia` (`id_provincia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `municipio`
--

/*!40000 ALTER TABLE `municipio` DISABLE KEYS */;
INSERT INTO `municipio` (`id_municipio`,`nome_municipio`,`id_provincia`) VALUES 
 (1,'Viana',1),
 (2,'Cacuaco',1),
 (3,'Maianga',1),
 (4,'Talatona',1),
 (5,'Chibia',2);
/*!40000 ALTER TABLE `municipio` ENABLE KEYS */;


--
-- Definition of table `provincia`
--

DROP TABLE IF EXISTS `provincia`;
CREATE TABLE `provincia` (
  `id_provincia` int(11) NOT NULL AUTO_INCREMENT,
  `nome_provincia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_provincia`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `provincia`
--

/*!40000 ALTER TABLE `provincia` DISABLE KEYS */;
INSERT INTO `provincia` (`id_provincia`,`nome_provincia`) VALUES 
 (1,'Luanda'),
 (2,'Lubango');
/*!40000 ALTER TABLE `provincia` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
