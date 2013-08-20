-- MySQL dump 10.13  Distrib 5.1.37, for Win32 (ia32)
--
-- Host: localhost    Database: predioiii
-- ------------------------------------------------------
-- Server version	5.1.37-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aberturafechamento`
--

DROP TABLE IF EXISTS `aberturafechamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aberturafechamento` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Data` char(10) DEFAULT NULL,
  `Operacao` varchar(20) DEFAULT NULL,
  `Hora` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aberturafechamento`
--

LOCK TABLES `aberturafechamento` WRITE;
/*!40000 ALTER TABLE `aberturafechamento` DISABLE KEYS */;
INSERT INTO `aberturafechamento` VALUES (1,'16/10/2012','Abertura','14:10:01'),(2,'22/10/2012','Abertura','13:24:34');
/*!40000 ALTER TABLE `aberturafechamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acesso`
--

DROP TABLE IF EXISTS `acesso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acesso` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `CODIGO_USUARIO` int(11) DEFAULT '0',
  `DATA` varchar(10) DEFAULT '',
  `HORA` varchar(10) DEFAULT NULL,
  `DESCRICAO` text,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acesso`
--

LOCK TABLES `acesso` WRITE;
/*!40000 ALTER TABLE `acesso` DISABLE KEYS */;
INSERT INTO `acesso` VALUES (1,0,'15/10/2012','14:27:58','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.'),(2,0,'15/10/2012','14:28:36','Acesso ao sistema não efetuado [Interface - Login]'),(3,0,'15/10/2012','14:28:41','Acesso ao sistema não efetuado [Interface - Login]'),(4,0,'15/10/2012','14:28:49','Acesso ao sistema não efetuado [Interface - Login]'),(5,1,'15/10/2012','14:29:13','Novo cadastro de usuário efetuado[Interface - Usuários]\nNome informado: valeria'),(6,1,'15/10/2012','14:29:39','Acesso ao sistema efetuado [Interface - Login]'),(7,0,'15/10/2012','14:30:07','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.'),(8,1,'15/10/2012','14:36:17','Acesso ao sistema efetuado [Interface - Login]'),(9,1,'15/10/2012','14:36:37','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(10,1,'15/10/2012','14:38:53','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(11,1,'15/10/2012','14:40:20','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(12,1,'15/10/2012','17:13:37','Acesso ao sistema efetuado [Interface - Login]'),(13,2,'15/10/2012','17:14:37','Novo cadastro de usuário efetuado[Interface - Usuários]\nNome informado: Debora Mendes'),(14,2,'15/10/2012','21:22:06','Acesso ao sistema efetuado [Interface - Login]'),(15,0,'15/10/2012','21:22:56','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.'),(16,1,'16/10/2012','07:52:27','Acesso ao sistema efetuado [Interface - Login]'),(17,1,'16/10/2012','09:06:18','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(18,1,'16/10/2012','09:07:02','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(19,1,'16/10/2012','09:07:23','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(20,1,'16/10/2012','09:08:06','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(21,1,'16/10/2012','09:09:30','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(22,1,'16/10/2012','10:03:57','Acesso ao sistema efetuado [Interface - Login]'),(23,1,'16/10/2012','10:20:11','Acesso a interface não efetuado[Interface - Contas à receber - Contratos]'),(24,1,'16/10/2012','10:20:15','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(25,0,'16/10/2012','10:38:41','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.'),(26,1,'16/10/2012','13:46:49','Acesso ao sistema efetuado [Interface - Login]'),(27,0,'16/10/2012','14:09:48','Acesso ao sistema não efetuado [Interface - Login]'),(28,1,'16/10/2012','14:09:53','Acesso ao sistema efetuado [Interface - Login]'),(29,1,'16/10/2012','17:27:29','Acesso a interface efetuado[Interface - Contas à receber - Contratos]'),(30,0,'16/10/2012','18:02:00','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.'),(31,1,'17/10/2012','09:15:00','Acesso ao sistema efetuado [Interface - Login]'),(32,0,'22/10/2012','13:28:37','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.'),(33,1,'22/10/2012','14:42:47','Acesso a exclusão efetuado [Interface - Caixa]\nCódigo 1 Operação Venda Valor 15.00 '),(34,0,'28/10/2012','01:58:51','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.'),(35,3,'28/10/2012','01:59:17','Acesso ao sistema não efetuado [Interface - Login]'),(36,3,'28/10/2012','01:59:21','Acesso ao sistema não efetuado [Interface - Login]'),(37,3,'28/10/2012','01:59:25','Acesso ao sistema efetuado [Interface - Login]'),(38,0,'28/10/2012','01:59:38','Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.');
/*!40000 ALTER TABLE `acesso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(100) DEFAULT NULL,
  `NASCIMENTO` varchar(10) DEFAULT NULL,
  `CPF` char(14) DEFAULT NULL,
  `TELEFONE` char(15) DEFAULT NULL,
  `ENDERECO` varchar(100) DEFAULT NULL,
  `NUMERO` varchar(10) DEFAULT NULL,
  `BAIRRO` varchar(100) DEFAULT NULL,
  `UF` char(2) DEFAULT NULL,
  `CIDADE` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `DESCONTO` float(5,2) DEFAULT NULL,
  `SEXO` varchar(10) DEFAULT NULL,
  `CELULAR` char(15) DEFAULT NULL,
  `CEP` char(9) DEFAULT NULL,
  `LOCALIDADE` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CODIGO`,`LOCALIDADE`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Elizama Santos Silva','04/11/1988','377.976.178-56','(38)3213-1603','Av Antonio Lafetá /rabelo','120','Santa Lucia','MG','Montes Claros','li_elizama@yahoo.com.br',0.00,'Feminino','(38)9229-2883','39702-734',0);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente_excluido`
--

DROP TABLE IF EXISTS `cliente_excluido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente_excluido` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(100) DEFAULT NULL,
  `NASCIMENTO` varchar(10) DEFAULT NULL,
  `CPF` char(14) DEFAULT NULL,
  `TELEFONE` char(15) DEFAULT NULL,
  `ENDERECO` varchar(100) DEFAULT NULL,
  `NUMERO` varchar(10) DEFAULT NULL,
  `BAIRRO` varchar(100) DEFAULT NULL,
  `UF` char(2) DEFAULT NULL,
  `CIDADE` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `DESCONTO` float(5,2) DEFAULT NULL,
  `SEXO` varchar(10) DEFAULT NULL,
  `CELULAR` char(15) DEFAULT NULL,
  `CEP` char(9) DEFAULT NULL,
  `LOCALIDADE` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CODIGO`,`LOCALIDADE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente_excluido`
--

LOCK TABLES `cliente_excluido` WRITE;
/*!40000 ALTER TABLE `cliente_excluido` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente_excluido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contas_pagar`
--

DROP TABLE IF EXISTS `contas_pagar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contas_pagar` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRICAO` varchar(60) DEFAULT NULL,
  `EMISSAO` char(10) DEFAULT NULL,
  `VENCIMENTO` char(10) DEFAULT NULL,
  `VALOR` float(10,2) DEFAULT NULL,
  `STATUS` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contas_pagar`
--

LOCK TABLES `contas_pagar` WRITE;
/*!40000 ALTER TABLE `contas_pagar` DISABLE KEYS */;
/*!40000 ALTER TABLE `contas_pagar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contas_receber`
--

DROP TABLE IF EXISTS `contas_receber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contas_receber` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRICAO` varchar(60) DEFAULT NULL,
  `EMISSAO` char(10) DEFAULT NULL,
  `VENCIMENTO` char(10) DEFAULT NULL,
  `VALOR` float(10,2) DEFAULT NULL,
  `STATUS` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contas_receber`
--

LOCK TABLES `contas_receber` WRITE;
/*!40000 ALTER TABLE `contas_receber` DISABLE KEYS */;
/*!40000 ALTER TABLE `contas_receber` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contas_receber_contrato`
--

DROP TABLE IF EXISTS `contas_receber_contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contas_receber_contrato` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_cliente` int(11) DEFAULT NULL,
  `codigo_turma` int(11) DEFAULT '0',
  `parcela` int(11) DEFAULT NULL,
  `data_emissao` char(10) DEFAULT '0000/00/00',
  `data_pagamento` varchar(10) DEFAULT NULL,
  `desconto` double(5,2) DEFAULT NULL,
  `valor` double(10,2) DEFAULT '0.00',
  `total` double(5,2) DEFAULT NULL,
  `valor_pago` double(10,2) DEFAULT '0.00',
  `juros` double(5,2) DEFAULT NULL,
  `codigo_contrato` int(11) DEFAULT NULL,
  `LOCALIDADE` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contas_receber_contrato`
--

LOCK TABLES `contas_receber_contrato` WRITE;
/*!40000 ALTER TABLE `contas_receber_contrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `contas_receber_contrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contrato`
--

DROP TABLE IF EXISTS `contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contrato` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_aluno` int(11) DEFAULT '0',
  `LOCALIDADE` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrato`
--

LOCK TABLES `contrato` WRITE;
/*!40000 ALTER TABLE `contrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `contrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contrato_parcela`
--

DROP TABLE IF EXISTS `contrato_parcela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contrato_parcela` (
  `codigo` int(11) NOT NULL DEFAULT '0',
  `codigo_contrato` int(11) DEFAULT NULL,
  `data` varchar(10) DEFAULT '0000-00-00',
  `parcela` int(11) DEFAULT NULL,
  `valor` float(11,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrato_parcela`
--

LOCK TABLES `contrato_parcela` WRITE;
/*!40000 ALTER TABLE `contrato_parcela` DISABLE KEYS */;
/*!40000 ALTER TABLE `contrato_parcela` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrada_saida`
--

DROP TABLE IF EXISTS `entrada_saida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entrada_saida` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUTO_CODIGO` int(11) DEFAULT NULL,
  `DATA_HORA` varchar(15) DEFAULT NULL,
  `TIPO` varchar(10) DEFAULT NULL,
  `QTDE` int(11) DEFAULT NULL,
  `PRECO` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrada_saida`
--

LOCK TABLES `entrada_saida` WRITE;
/*!40000 ALTER TABLE `entrada_saida` DISABLE KEYS */;
/*!40000 ALTER TABLE `entrada_saida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradacaixa`
--

DROP TABLE IF EXISTS `entradacaixa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entradacaixa` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Data` varchar(10) NOT NULL DEFAULT '',
  `Hora` varchar(10) DEFAULT NULL,
  `Operacao` varchar(50) DEFAULT NULL,
  `CodigoProduto` int(11) DEFAULT NULL,
  `Valor` float(10,2) DEFAULT NULL,
  `CodigoAbertura` int(11) NOT NULL DEFAULT '0',
  `Quantidade` int(10) DEFAULT NULL,
  PRIMARY KEY (`Codigo`,`Data`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradacaixa`
--

LOCK TABLES `entradacaixa` WRITE;
/*!40000 ALTER TABLE `entradacaixa` DISABLE KEYS */;
/*!40000 ALTER TABLE `entradacaixa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradacaixa_excluido`
--

DROP TABLE IF EXISTS `entradacaixa_excluido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entradacaixa_excluido` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Data` varchar(10) NOT NULL DEFAULT '',
  `Hora` varchar(10) DEFAULT NULL,
  `Operacao` varchar(50) DEFAULT NULL,
  `CodigoProduto` int(11) DEFAULT NULL,
  `Valor` float(10,2) DEFAULT NULL,
  `CodigoAbertura` int(11) NOT NULL DEFAULT '0',
  `Quantidade` int(10) DEFAULT NULL,
  PRIMARY KEY (`Codigo`,`Data`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradacaixa_excluido`
--

LOCK TABLES `entradacaixa_excluido` WRITE;
/*!40000 ALTER TABLE `entradacaixa_excluido` DISABLE KEYS */;
INSERT INTO `entradacaixa_excluido` VALUES (1,'22/10/2012','14:42:23','Venda',7,15.00,1,1);
/*!40000 ALTER TABLE `entradacaixa_excluido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedor` (
  `CODIGO` int(6) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(100) DEFAULT NULL,
  `ENDERECO` varchar(100) DEFAULT NULL,
  `NUMERO` varchar(10) DEFAULT NULL,
  `COMPLEMENTO` varchar(20) DEFAULT NULL,
  `BAIRRO` varchar(60) DEFAULT NULL,
  `CIDADE` varchar(100) DEFAULT NULL,
  `CEP` char(9) DEFAULT NULL,
  `PABX` char(13) DEFAULT NULL,
  `FAX` char(13) DEFAULT NULL,
  `CPF` char(14) DEFAULT NULL,
  `CNPJ` char(18) DEFAULT NULL,
  `HOME_PAGE` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'Grafica','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(2,'J L Informatica','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(3,'Palimontes Papelaria','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(4,'Marcos Andre','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localidade`
--

DROP TABLE IF EXISTS `localidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localidade` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(100) DEFAULT NULL,
  `cidade` varchar(100) DEFAULT NULL,
  `endereco` varchar(100) DEFAULT NULL,
  `numero` varchar(10) DEFAULT NULL,
  `complemento` varchar(60) DEFAULT NULL,
  `cep` char(9) DEFAULT NULL,
  `situacao` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localidade`
--

LOCK TABLES `localidade` WRITE;
/*!40000 ALTER TABLE `localidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `localidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matricula`
--

DROP TABLE IF EXISTS `matricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matricula` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `ALUNO` int(11) DEFAULT '0',
  `TURMA` int(11) DEFAULT '0',
  `LOCALIDADE` int(11) DEFAULT '0',
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matricula`
--

LOCK TABLES `matricula` WRITE;
/*!40000 ALTER TABLE `matricula` DISABLE KEYS */;
/*!40000 ALTER TABLE `matricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matricula_excluido`
--

DROP TABLE IF EXISTS `matricula_excluido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matricula_excluido` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `ALUNO` int(11) DEFAULT '0',
  `TURMA` int(11) DEFAULT '0',
  `LOCALIDADE` int(11) DEFAULT '0',
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matricula_excluido`
--

LOCK TABLES `matricula_excluido` WRITE;
/*!40000 ALTER TABLE `matricula_excluido` DISABLE KEYS */;
/*!40000 ALTER TABLE `matricula_excluido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `FORNECEDOR_CODIGO` int(11) NOT NULL DEFAULT '0',
  `NOME` varchar(100) DEFAULT NULL,
  `MARCA` varchar(60) DEFAULT NULL,
  `ESTOQUE` int(11) DEFAULT NULL,
  `ESTOQUE_CRITICO` int(11) DEFAULT NULL,
  `PRECO` double(10,2) DEFAULT NULL,
  `PRECO_VENDA` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professor` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(100) DEFAULT NULL,
  `ENDERECO` varchar(100) DEFAULT NULL,
  `NUMERO` varchar(10) DEFAULT NULL,
  `COMPLEMENTO` varchar(20) DEFAULT NULL,
  `BAIRRO` varchar(60) DEFAULT NULL,
  `CIDADE` varchar(100) DEFAULT NULL,
  `CEP` char(9) DEFAULT NULL,
  `PABX` char(13) DEFAULT NULL,
  `FAX` char(13) DEFAULT NULL,
  `CPF` char(14) DEFAULT NULL,
  `CNPJ` char(18) DEFAULT NULL,
  `HOME_PAGE` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor`
--

LOCK TABLES `professor` WRITE;
/*!40000 ALTER TABLE `professor` DISABLE KEYS */;
INSERT INTO `professor` VALUES (1,'Leticia Frois','',NULL,NULL,'','',NULL,'(38)3221-0236','(38)9182-9127',NULL,NULL,NULL,'lletsgo@hotmail.com'),(2,'Taciane','',NULL,NULL,'','',NULL,'(38)8403-8398','(38)3217-8908',NULL,NULL,NULL,'tacyplim@yahoo.com.br'),(3,'John','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(4,'Rosemary','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(5,'Reinaldo','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(6,'Alisson','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(7,'Daniele','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(8,'Jacqueline','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(9,'Madalena','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(10,'Karine','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(11,'Cecilia Caroline','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(12,'Vanessa','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(13,'Flaviane','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL),(14,'Zenaide','',NULL,NULL,'','',NULL,'(  )    -','(  )    -',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor_excluido`
--

DROP TABLE IF EXISTS `professor_excluido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professor_excluido` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(100) DEFAULT NULL,
  `ENDERECO` varchar(100) DEFAULT NULL,
  `NUMERO` varchar(10) DEFAULT NULL,
  `COMPLEMENTO` varchar(20) DEFAULT NULL,
  `BAIRRO` varchar(60) DEFAULT NULL,
  `CIDADE` varchar(100) DEFAULT NULL,
  `CEP` char(9) DEFAULT NULL,
  `PABX` char(13) DEFAULT NULL,
  `FAX` char(13) DEFAULT NULL,
  `CPF` char(14) DEFAULT NULL,
  `CNPJ` char(18) DEFAULT NULL,
  `HOME_PAGE` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor_excluido`
--

LOCK TABLES `professor_excluido` WRITE;
/*!40000 ALTER TABLE `professor_excluido` DISABLE KEYS */;
/*!40000 ALTER TABLE `professor_excluido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recibos`
--

DROP TABLE IF EXISTS `recibos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recibos` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `VALOR` double(10,2) DEFAULT '0.00',
  `MATRICULA` int(11) NOT NULL DEFAULT '0',
  `DATA` char(20) DEFAULT '0000/00/00',
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recibos`
--

LOCK TABLES `recibos` WRITE;
/*!40000 ALTER TABLE `recibos` DISABLE KEYS */;
/*!40000 ALTER TABLE `recibos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saidacaixa`
--

DROP TABLE IF EXISTS `saidacaixa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saidacaixa` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Data` varchar(15) DEFAULT NULL,
  `Hora` varchar(10) DEFAULT NULL,
  `Operacao` varchar(50) DEFAULT NULL,
  `Valor` float(10,2) DEFAULT NULL,
  `CodigoAbertura` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saidacaixa`
--

LOCK TABLES `saidacaixa` WRITE;
/*!40000 ALTER TABLE `saidacaixa` DISABLE KEYS */;
/*!40000 ALTER TABLE `saidacaixa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subprodutos`
--

DROP TABLE IF EXISTS `subprodutos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subprodutos` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Categoria` varchar(30) NOT NULL DEFAULT '',
  `Nome` varchar(150) DEFAULT NULL,
  `Quantidade` int(6) unsigned DEFAULT NULL,
  `QuantidadeMin` int(6) unsigned DEFAULT NULL,
  `PrecoCompra` float(10,2) DEFAULT NULL,
  `PrecoVenda` float(10,2) DEFAULT NULL,
  `Retirada` smallint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subprodutos`
--

LOCK TABLES `subprodutos` WRITE;
/*!40000 ALTER TABLE `subprodutos` DISABLE KEYS */;
INSERT INTO `subprodutos` VALUES (1,'UNIDADES','Apostila Inglês Básico Modulo 01',10,4,2.00,10.00,1),(2,'UNIDADES','Apostila Inglês Básico Modulo 02',12,4,2.00,10.00,1),(3,'UNIDADES','Apostila Inglês Básico Modulo 03',12,4,2.00,10.00,1),(4,'UNIDADES','Apostila Inglês Básico Modulo 04',27,4,2.00,10.00,1),(5,'UNIDADES','Apostila Inglês Básico Modulo 05',7,2,2.00,10.00,1),(6,'UNIDADES','Apostila Inglês Básico Modulo 06 (Final Book)',16,4,2.00,10.00,1),(7,'UNIDADES','Minidicionário Escolar Inglês ',12,4,6.00,15.00,1);
/*!40000 ALTER TABLE `subprodutos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turma`
--

DROP TABLE IF EXISTS `turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `turma` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `DIA` varchar(20) DEFAULT NULL,
  `HI` char(5) DEFAULT NULL,
  `HF` char(5) DEFAULT NULL,
  `FUNCIONARIO` int(11) DEFAULT '0',
  `CURSO` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turma`
--

LOCK TABLES `turma` WRITE;
/*!40000 ALTER TABLE `turma` DISABLE KEYS */;
INSERT INTO `turma` VALUES (1,'Segunda','08:00','10:00',2,'Inglês Infantil'),(2,'Segunda','19:00','21:00',1,'Inglês Básico'),(3,'Terça','08:00','10:00',1,'Inglês Básico'),(4,'Terça','08:00','10:00',13,'Inglês nfantil'),(5,'Terça','14:00','16:00',1,'Inglês Básico'),(6,'Terça','16:00','18:00',5,'Inglês Básico'),(7,'Terça','16:00','18:00',1,'Inglês Básico'),(8,'Terça','19:00','21:00',12,'Inglês Básico'),(9,'Terça','19:00','21:00',5,'Inglês Básico'),(10,'Quarta','08:00','10:00',2,'Inglês Básico'),(11,'Quarta','14:00','16:00',1,'Inglês Básico'),(12,'Quarta','14:00','16:00',9,'Inglês Básico'),(13,'Quarta','16:00','18:00',1,'Inglês Básico'),(14,'Quarta','19:00','21:00',1,'Inglês Básico'),(15,'Quinta','14:00','16:00',1,'Inglês Básico'),(16,'Quinta','14:00','16:00',5,'Inglês Básico'),(17,'Quinta','16:00','18:00',5,'Inglês Básico'),(18,'Quinta','19:00','21:00',1,'Inglês Básico'),(19,'Sexta','08:00','10:00',2,'Inglês Básico'),(20,'Sexta','08:00','10:00',5,'Inglês Básico'),(21,'Sábado','07:00','09:00',1,'Inglês Básico'),(22,'Sábado','08:00','10:00',8,'Inglês Básico'),(23,'Sábado','09:00','11:00',1,'Inglês Básico'),(24,'Sábado','10:00','12:00',14,'Inglês Intermediário'),(25,'Sexta','10:00','12:00',8,'Inglês Básico'),(26,'Sábado','13:00','15:00',5,'Inglês Básico'),(27,'Sábado','13:00','15:00',4,'Inglês Básico'),(28,'Sábado','15:00','17:00',4,'Inglês Básico'),(29,'Sábado','15:00','17:00',5,'Inglês Básico'),(30,'Sábado','15:00','17:00',8,'Inglês Básico');
/*!40000 ALTER TABLE `turma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` char(100) DEFAULT NULL,
  `USUARIO` char(30) NOT NULL DEFAULT '',
  `SENHA` char(32) NOT NULL DEFAULT '',
  `TIPO` char(30) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'valeria','valeria','cd2574b1a5a1e91641693d44ca769389','Administrador'),(2,'Debora Mendes','mendes','c3885a42b0de78a8020f41804d28f040','Vendedor (a)'),(3,'Guilherme Augusto Rodrigues','gui','f5bb0c8de146c67b44babbf4e6584cc0','Administrador');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valorinicial`
--

DROP TABLE IF EXISTS `valorinicial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valorinicial` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Data` varchar(15) DEFAULT NULL,
  `Valor` float(10,2) DEFAULT NULL,
  `CodigoFornecedor` int(11) NOT NULL DEFAULT '0',
  `Hora` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valorinicial`
--

LOCK TABLES `valorinicial` WRITE;
/*!40000 ALTER TABLE `valorinicial` DISABLE KEYS */;
INSERT INTO `valorinicial` VALUES (1,'16/10/2012',0.00,1,'14:10:01'),(2,'22/10/2012',16.00,2,'13:24:34');
/*!40000 ALTER TABLE `valorinicial` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-11-12 17:22:55
