DELIMITER $$

DROP PROCEDURE IF EXISTS `SP_OID_GENERIC` $$
CREATE PROCEDURE `SP_OID_GENERIC`()
begin
    insert into GENERIC_OID_SEQ values();
    delete from GENERIC_OID_SEQ where SEQUENCE!=@@IDENTITY;
    commit;
    select @@IDENTITY as OID;
end $$

DELIMITER ;



DELIMITER $$

DROP PROCEDURE IF EXISTS `SP_OID_ITEM` $$
CREATE PROCEDURE `SP_OID_ITEM`()
begin
    insert into ITEM_OID_SEQ values();
    delete from ITEM_OID_SEQ where SEQUENCE!=@@IDENTITY;
    commit;
    select @@IDENTITY as OID;
end $$

DELIMITER ;