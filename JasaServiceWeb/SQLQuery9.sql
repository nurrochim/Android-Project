USE [JasaServiceDB]
GO

/****** Object:  StoredProcedure [dbo].[SELECT_REQUEST_ACCEPT]    Script Date: 12/14/2016 01:41:20 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SELECT_REQUEST_ACCEPT]
@ID_REQUEST VARCHAR(100),
@ID_USER_PENYEDIA VARCHAR(50)
AS
BEGIN
SELECT A.ID_REQUEST, A.FID_SERVICE, D.ID_SERVICE_PROVIDE,A.FID_USER_CREATE, B.USER_NAME, B.FOTO_PROFIL, B.NO_TELFON, A.STATUS
FROM TBL_REQUEST A LEFT JOIN TBL_USER B ON A.FID_USER_CREATE = B.ID_USER 
LEFT JOIN TBL_SERVICE C ON A.FID_SERVICE = C.ID_SERVICE
LEFT JOIN TBL_SERVICE_PROVIDE D ON A.FID_SERVICE = D.FID_SERVICE AND D.FID_USER = @ID_USER_PENYEDIA
WHERE ID_REQUEST = @ID_REQUEST
END

GO


