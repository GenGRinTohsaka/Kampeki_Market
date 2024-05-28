drop database if exists DBKampekiMarket;
create database DBKampekiMarket;
use DBKampekiMarket;

create table Clientes(
	codigoCliente int not null,
    nitCliente varchar(10) not null,
    nombreCliente varchar(50) not null,
    apellidoCliente varchar(50) not null,
    direccionCliente varchar(150),
    telefonoCliente varchar(55),
    correoCliente varchar(45),
    primary key PK_codigoCliente(codigoCliente)
);

create table Proveedores(		
	codigoProveedor int not null,
    nitProveedor varchar(10) not null,
    nombreProveedor varchar(60) not null,
    apellidoProveedor varchar(50) not null,
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor(codigoProveedor)
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_codigoProveedor foreign key (codigoProveedor)
     references Proveedores(codigoProveedor)
);

create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_codigoEmailProveedor (codigoEmailProveedor),
    constraint FK_codigoProveedores foreign key (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table TipoProducto(
	codigoTipoProducto int not null,
    descripcion varchar(45),
    primary key PK_codigoTipoProducto (codigoTipoProducto)
);

create table Productos(
	codigoProducto varchar(15) not null,
    descripcionProducto varchar(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    imagenProducto varchar(45),
    existencia int,
	codigoTipoProducto int,
    codigoProveedor int,
    primary key PK_codigoProducto(codigoProducto),
    constraint FK_codigoTipoProducto_Productos foreign key (codigoTipoProducto)
		references TipoProducto(codigoTipoProducto),
	constraint FK_codigoProveedor_Productos foreign key (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numeroDocumento)
);

create table DetalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_codigoDetalleCompra(codigoDetalleCompra),
    constraint FK_codigoProducto_DetalleCompra foreign key (codigoProducto)
		references Productos(codigoProducto),
	constraint FK_numeroDocumento_DetalleCompra foreign key (numeroDocumento)
		references Compras(numeroDocumento)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);

create table Empleados(
	codigoEmpleado int not null,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int not null,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint FK_codigoCargoEmpleado_Empleados foreign key (codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
);

create table Factura(
	numeroFactura int not null,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int not null,
    codigoEmpleado int not null,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_codigoCliente_Factura foreign key (codigoCliente)
		references Clientes(codigoCliente),
	constraint FK_codigoEmpleado_Factura foreign key (codigoEmpleado)
		references Empleados(codigoEmpleado)
);

create table DetalleFactura(
	codigoDetalleFactura int not null,
    precioUnitario decimal(10,2),
    cantidad int,
	numeroFactura int not null,
    codigoProducto varchar(15),
    primary key PK_codigoDetalleFactura(codigoDetalleFactura),
    constraint FK_numeroFactura_DetalleFactura foreign key (numeroFactura)
		references Factura(numeroFactura),
	constraint FK_codigoProducto_Productos foreign key(codigoProducto)
		references Productos(codigoProducto)
);

Delimiter $$
create procedure sp_AgregarClientes(in codigoCliente int ,nitCliente varchar(10),nombreCliente varchar(50),apellidoCliente varchar(50),direccionCliente varchar(150),telefonoCliente varchar(55),correoCliente varchar(45))
	Begin
		Insert Into Clientes(codigoCliente,nitCliente,nombreCliente,apellidoCliente,direccionCliente,telefonoCliente,correoCliente)
        values (codigoCliente,nitCliente,nombreCliente,apellidoCliente,direccionCliente,telefonoCliente,correoCliente);
	End $$
Delimiter ;

call sp_AgregarClientes(01,'180181','Harol','Luna','San Raymundo','28651030','harol@gmail.com');
call sp_AgregarClientes(02,'117481','Rafael','Samayoa','Amatitlan','0121030','rafa@gmail.com');


Delimiter $$
	create procedure sp_ListarClientes()
    Begin
		select 
        C.codigoCliente,
        C.nitCliente,
        C.nombreCliente,
        C.apellidoCliente,
        C.direccionCliente,
        C.telefonoCliente,
        C.correoCliente
        from Clientes C;
	End $$
Delimiter ;



call sp_ListarClientes();



Delimiter $$
	create procedure sp_BuscarClientes(in  _codigoCliente int)
    Begin
		select 
        C.codigoCliente,
        C.nitCliente,
        C.nombreCliente,
        C.apellidoCliente,
        C.direccionCliente,
        C.telefonoCliente,
        C.correoCliente
        from Clientes C
        Where codigoCliente = _codigoCliente;
	End $$
Delimiter ;

call sp_BuscarClientes(1);


Delimiter $$
	create procedure sp_EliminarClientes(in _codigoCliente int)
    Begin
		Delete from Clientes
        Where codigoCliente = _codigoCliente;
	End $$
Delimiter ;



Delimiter $$
	create procedure sp_EditarClientes( in _codigoCliente int , _nitCliente varchar(10), _nombreCliente varchar(50), _apellidoCliente varchar(50),
    _direccionCliente varchar(150), _telefonoCliente varchar(55), _correoCliente varchar(45))
    Begin
		Update Clientes C
			set
		C.nitCliente = _nitCliente,
        C.nombreCliente = _nombreCliente,
        C.apellidoCliente = _apellidoCliente,
        C.direccionCliente = _direccionCliente,
        C.telefonoCliente = _telefonoCliente,
        C.correoCliente = _correoCliente
        Where codigoCliente = _codigoCliente;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarProveedores(in codigoProveedor int, in nitProveedor varchar(10), in nombreProveedor varchar(60), in apellidoProveedor varchar(150), in direccionProveedor varchar(150), 
    in razonSocial varchar(60), in contactoPrincipal varchar(100), in paginaWeb varchar(50))
    Begin
		Insert Into Proveedores(codigoProveedor,nitProveedor,nombreProveedor,apellidoProveedor,direccionProveedor,razonSocial,contactoPrincipal,paginaWeb)
        values (codigoProveedor,nitProveedor,nombreProveedor,apellidoProveedor,direccionProveedor,razonSocial,contactoPrincipal,paginaWeb);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarProveedores()
    Begin
		select
        P.codigoProveedor,
        P.nitProveedor,
        P.nombreProveedor,
        P.apellidoProveedor,
        P.direccionProveedor,
        P.razonSocial,
        P.contactoPrincipal,
        P.paginaWeb
        from Proveedores P;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarProveedor(in _codigoProveedor int)
    Begin
		Delete from Proveedores
        Where codigoProveedor = _codigoProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarProveedores(in _codigoProveedor int, in _nitProveedor varchar(10), in _nombreProveedor varchar(60), in _apellidoProveedor varchar(150), in _direccionProveedor varchar(150), 
    in _razonSocial varchar(60), in _contactoPrincipal varchar(100), in _paginaWeb varchar(50))
    Begin
		Update Proveedores P
			set
			P.nitProveedor = _nitProveedor,
            P.nombreProveedor = _nombreProveedor,
            P.apellidoProveedor = _apellidoProveedor,
            P.direccionProveedor = _direccionProveedor,
            P.razonSocial = _razonSocial,
            P.contactoPrincipal = _contactoPrincipal,
            P.paginaWeb = _paginaWeb
            Where P.codigoProveedor = _codigoProveedor;
        
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarTelefonoProveedor(in codigoTelefonoProveedor int,in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
    Begin
		Insert Into TelefonoProveedor(codigoTeleonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor)
        values (codigoTeleonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarTelefonoProveedor()
    Begin
		Select
			TP.codigoTelefonoProveedor,
            TP.numeroPrincipal,
            TP.numeroSecundario,
            TP.observaciones,
            TP.codigoProveedor
		from TelefonoProveedor TP;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarTelefonoProveedor(in _codigoTelefonoProveedor int,in _numeroPrincipal varchar(8), in _numeroSecundario varchar(8), in _observaciones varchar(45), in _codigoProveedor int)
    Begin
		update TelefonoProveedor TP 
			set 
			 TP.numeroPrincipal = _numeroPrincipal,
             TP.numeroSecundario = _numeroSecundario,
			 TP.observaciones = _observaciones,
             TP.codigoProveedor = _codigoProveedor
		    where TP.codigoTelefonoProveedor = _codigoTelefonoProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarTelefonoProveedor(in _codigoTelefonoProveedor int)
    Begin
		Delete From TelefonoProveedor
        where codigoTelefonoProveedor = _codigoTelefonoProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)	
    Begin
		Insert Into EmailProveedor(codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor)
        values (codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarEmailProveedor()
    Begin
		Select
			EP.codigoEmailProveedor,
            EP.emailProveedor,
            EP.descripcion,
            EP.codigoProveedor
		from EmailProveedor EP;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarEmailProveedor(in _codigoEmailProveedor int, in _emailProveedor varchar(50), in _descripcion varchar(100), in _codigoProveedor int)
    Begin
		update EmailProveedor EP
		set
			EP.emailProveedor = _emailProveedor,
            EP.descripcion = _descripcion,
            EP.codigoProveedor = _codigoProveedor
		where EP.codigoEmailProveedor = _codigoEmailProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarEmailProveedor(in _codigoEmailProveedor int)
    Begin
		Delete from EmailProveedor 
        where codigoEmailProveedor = _codigoEmailProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
    Begin
		Insert Into TipoProducto(codigoTipoProducto,descripcion)
        values(codigoTipoProducto,descripcion);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarTipoProducto()
    Begin
		select
			TP.codigoTipoProducto,
            Tp.descripcion
		from TipoProducto TP;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarTipoProducto(in _codigoTipoProducto int, in _descripcion varchar(45))
    Begin
		update TipoProducto TP
        set
			TP.descripcion = _descripcion
		where TP.codigoTipoProducto = _codigoTipoProducto;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarTipoProducto(in _codigoTipoProducto int)
    Begin
		Delete from TipoProducto 
        where codigoTipoProducto = _codigoTipoProducto;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarCompras(in numeroDocumento int, in fechaDocumento date,in descripcion varchar(60), in totalDocumento decimal(10,2))
    Begin
		Insert Into Compras(numeroDocumento,fechaDocumento,descripcion,totalDocumento)
        values (numeroDocumento,fechaDocumento,descripcion,totalDocumento);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarCompras()
    Begin
		select 
			C.numeroDocumento,
            C.fechaDocumento,
            C.descripcion,
            C.totalDocumento
		from Compras C;
    End $$
Delimiter ;


Delimiter $$
	create procedure sp_EditarCompras(in _numeroDocumento int, in _fechaDocumento date,in _descripcion varchar(60), in _totalDocumento decimal(10,2))
    Begin
		update Compras C
        set
			C.fechaDocumento = _fechaDocumento,
            C.descripcion = _descripcion,
            C.totalDocumento = _totalDocumento
		where C.numeroDocumento = _numeroDocumento;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarCompras(in _numeroDocumento int)
    Begin
		select 
			C.numeroDocumento,
            C.fechaDocumento,
            C.descripcion,
            C.totalDocumento
		from Compras C
        where C.numeroDocumento = _numeroDocumento;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarCompras(in _numeroDocumento int)
    Begin
		Delete from Compras
        where numeroDocumento = _numeroDocumento;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarCargoEmpleado(in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
    Begin
		Insert Into CargoEmpleado(codigoCargoEmpleado,nombreCargo,descripcionCargo)
        values (codigoCargoEmpleado,nombreCargo,descripcionCargo);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarCargoEmpleado()
    Begin
		select
			CE.codigoCargoEmpleado,
            CE.nombreCargo,
            CE.descripcionCargo
		from CargoEmpleado CE;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarCargoEmpleado(_codigoCargoEmpleado int)
    Begin
		select
			CE.codigoCargoEmpleado,
            CE.nombreCargo,
            CE.descripcionCargo
		from CargoEmpleado CE
        where CE.codigoCargoEmpleado = _codigoCargoEmpleado;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarCargoEmpleado(in _codigoCargoEmpleado int, in _nombreCargo varchar(45), in _descripcionCargo varchar(45))
    Begin
		update CargoEmpleado CE
        set
			CE.nombreCargo = _nombreCargo,
            CE.descripcionCargo = _descripcionCargo
		where CE.codigoCargoEmpleado = _codigoCargoEmpleado;
    End $$
Delimiter ;


Delimiter $$
	create procedure sp_EliminarCargoEmpleado(in _codigoCargoEmpleado int)
    Begin 
		Delete from CargoEmpleado
        where codigoCargoEmpleado = _codigoCargoEmpleado;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarProducto(in codigoProducto varchar(15), in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(45),
    in existencia int, in codigoTipoProducto int, in codigoProveedor int)
    Begin
		Insert Into Productos(codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor)
        values(codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarProductos()
    Begin
		select
			P.codigoProducto,
            P.descripcionProducto,
            P.precioUnitario,
            P.precioDocena,
            P.precioMayor,
            P.imagenProducto,
            P.existencia,
            P.codigoTipoProducto,
            P.codigoProveedor
		from Productos P;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarProductos(in _codigoProducto varchar(15))
    Begin
		select
			P.codigoProducto,
            P.descripcionProducto,
            P.precioUnitario,
            P.precioDocena,
            P.precioMayor,
            P.imagenProducto,
            P.existencia,
            P.codigoTipoProducto,
            P.codigoProveedor
		from Productos P
        where P.codigoProducto = _codigoProducto;
    End $$
Delimiter ;

Delimiter $$

	create procedure sp_buscarTipoProducto (in _codigoTipoProducto int)
		Begin
			select
				TP.codigoTipoProducto,
				TP.descripcion
			from
				TipoProducto TP
			where TP.codigoTipoProducto = _codigoTipoProducto;
	End $$
Delimiter ;

Delimiter $$
	create procedure  sp_editarProducto(in _codigoProducto varchar(15), in _descripcionProducto varchar(45), in _precioUnitario decimal(10,2), in _precioDocena decimal(10,2), in _precioMayor decimal(10,2), in _imagenProducto varchar(45),
    in _existencia int, in _codigoTipoProducto int, in _codigoProveedor int)
    Begin
		Update productos P
			set
				P.descripcionProducto = _descripcionProducto,
				P.precioUnitario = _precioUnitario,
                P.precioDocena = _precioDocena,
                P.precioMayor = _precioMayor,
                P.imagenProducto = _imagenProducto,
                P.existencia = _existencia,
                P.codigoTipoProducto = _codigoTipoProducto,
                P.codigoProveedor = _codigoProveedor
			where P.codigoProducto = _codigoProducto;
				
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarProveedor (in _codigoProveedor int)
		Begin
			select
				P.codigoProveedor,
                P.nitProveedor,
                P.nombreProveedor,
                P.apellidoProveedor,
                P.direccionProveedor,
                P.razonSocial,
                P.contactoPrincipal,
                P.paginaWeb
			from
				Proveedores P
			where _codigoProveedor = P.codigoProveedor;
        End $$
Delimiter ;
-- -------------------------------------------------------------Detalle Compra --------------------------------------------------------------------
Delimiter $$
	create procedure sp_AgregarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
		Begin
			Insert Into detallecompra(codigoDetalleCompra,costoUnitario,cantidad,codigoProducto,numeroDocumento)
				values (codigoDetalleCompra,costoUnitario,cantidad,codigoProducto,numeroDocumento);
        End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarDetalleCompra()
    Begin
		Select 
			DC.codigoDetalleCompra,
			DC.costoUnitario,
            DC.cantidad,
            DC.codigoProducto,
            DC.numeroDocumento
		from DetalleCompra DC;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarDetalleCompra(in _codigoDetalleCompra int)
		Begin
			Select 
				DC.codigoDetalleCompra,
				DC.costoUnitario,
				DC.cantidad,
				DC.codigoProducto,
				DC.numeroDocumento
			from DetalleCompra DC
			where DC.codigoDetalleCompra = _codigoDetalleComrpa;
        End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarDetalleCompra(in _codigoDetalleCompra int, in _costoUnitario decimal(10,2), in _cantidad int, in _codigoProducto varchar(15), in numeroDocumento int)
		Begin
			Update DetalleCompra DC
				set
					DC.costoUnitario = _costoUnitario,
                    DC.cantidad = _cantidad,
                    DC.codigoProducto = _codigoProducto,
                    DC.numeroDocumento = _numeroDocumento
				where DC.codigoDetalleCompra = _codigoDetalleCompra;
        End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarDetalleCompra(in _codigoDetalleCompra int)
    Begin
		Delete from DetalleCompra
        where codigoDetalleCompra = _codigoDetalleCompra;
    End $$
Delimiter ;
-- -----------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------- FACTURA -------------------------------------------------------------------
-- ----------------------------------- AGREGAR ------------------------------------
Delimiter $$
	create procedure sp_AgregarFactura(in numeroFactura int, estado varchar(50), totalFactura decimal(10,2), fechaFactura varchar(45), codigoCliente int, codigoEmpleado int)
    Begin
		Insert into Factura(numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado)
        values(numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado);
	End $$
Delimiter ;

-- ----------------------------------- LISTAR ---------------------------------------
Delimiter $$
	create procedure sp_ListarFactura()
    Begin
		Select
		F.numeroFactura,
		F.estado,
		F.totalFactura,
		F.fechaFactura,
		F.codigoCliente,
		F.codigoEmpleado
		From Factura F;
    End $$
Delimiter ;

-- ----------------------------------- BUSCAR -----------------------------------------
Delimiter $$
	create procedure sp_BuscarFactura(in _numeroFactura int)
    Begin
		Select
        F.numeroFactura,
		F.estado,
		F.totalFactura,
		F.fechaFactura,
		F.codigoCliente,
		F.codigoEmpleado
		From Factura F 
        Where F.numeroFactura = _numeroFactura;
	End $$
Delimiter ;
        
-- ---------------------------------- ELIMINAR ---------------------------------------
Delimiter $$
	create procedure sp_EliminarFactura(in numeroFactura int)
    Begin
		Delete from Factura 
        Where numeroFactura = numeroFactura;
	End $$
Delimiter ;

-- --------------------------------- EDITAR ------------------------------------------
Delimiter $$
	create procedure sp_EditarFactura(in _numeroFactura int, _estado varchar(50), _totalFactura decimal(10,2), _fechaFactura varchar(45), _codigoCliente int, _codigoEmpleado int)
    Begin
		Update Factura F
        set
        estado = _estado,
        totalFactura = _totalFactura,
        fechaFactura = _fechaFactura,
        codigoCliente = _codigoCliente,
        codigoEmpleado = _codigoEmpleado
        Where numeroFactura = _numeroFactura;
	End $$
Delimiter ;

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------- Empleados -------------------------------------------------------------------------------------------------
Delimiter $$
	create procedure sp_AgregarEmpleados(in codigoEmpleado int,in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2),  in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
    Begin
		Insert into Empleados(codigoEmpleado,nombresEmpleado,apellidosEmpleado,sueldo,direccion,turno,codigoCargoEmpleado)
			values(codigoEmpleado,nombresEmpleado,apellidosEmpleado,sueldo,direccion,turno,codigoCargoEmpleado);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarEmpleados()
    Begin
		Select
			E.codigoEmpleado,
            E.nombresEmpleado,
            E.apellidosEmpleado,
            E.sueldo,
            E.direccion,
            E.turno,
            E.codigoCargoEmpleado
		from Empleados E;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarEmpleados(in _codigoEmpleado int)
	Begin
		Select
			E.codigoEmpleado,
            E.nombresEmpleado,
            E.apellidosEmpleado,
            E.sueldo,
            E.direccion,
            E.turno,
            E.codigoCargoEmpleado
		from Empleados E
        where E.codigoEmpleado = _codigoEmpleado;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarEmpleados(in _codigoEmpleado int,in _nombresEmpleado varchar(50), in _apellidosEmpleado varchar(50), in _sueldo decimal(10,2),  in _direccion varchar(150), in _turno varchar(15), in _codigoCargoEmpleado int)
    Begin
		Update Empleados E
        Set
            E.nombresEmpleado = _nombresEmpleado,
            E.apellidosEmpleado = _apellidosEmpleado,
            E.sueldo = _sueldo,
            E.direccion = _direccion,
            E.turno = _turno,
            E.codigoCargoEmpleado = _codigoCargoEmpleado
		where E.codigoEmpleado = _codigoEmpleado;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarEmpleados(in _codigoEmpleado int)
    Begin
		Delete from Empleados
        where codigoEmpleado = _codigoEmpleado;
    End $$
Delimiter ;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------- Detalle Factura ----------------------------------------------------------------------------------------------------------------
Delimiter $$
	create procedure sp_AgregarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int,in numeroFactura int ,in codigoProducto varchar(15))
	Begin
		Insert into DetalleFactura(codigoDetalleFactura,precioUnitario,cantidad,numeroFactura,codigoProducto)
			values(codigoDetalleFactura,precioUnitario,cantidad,numeroFactura,codigoProducto);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarDetalleFactura()
    Begin
		Select
			DF.codigoDetalleFactura,
			DF.precioUnitario,
            DF.cantidad,
            DF.numeroFactura,
            DF.codigoProducto
         from DetalleFactura DF;   
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_BuscarDetalleFactura(in _codigoDetalleFactura int)
    Begin
		Select
			DF.codigoDetalleFactura,
			DF.precioUnitario,
            DF.cantidad,
            DF.numeroFactura,
            DF.codigoProducto
         from DetalleFactura DF 
         where DF.codigoDetalleFactura = _codigoDetalleFactura;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarDetalleFactura(in _codigoDetalleFactura int, in _precioUnitario decimal(10,2), in _cantidad int,in _numeroFactura int ,in _codigoProducto varchar(15))
    Begin
		Update DetalleFactura DF
        Set
			DF.precioUnitario = _precioUnitario,
            DF.cantidad = _cantidad,
            DF.numeroFactura = _numeroFactura,
            DF.codigoProducto = _codigoProducto
		where DF.codigoDetalleFactura = _codigoDetalleFactura;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarDetalleFactura(in _codigoDetalleFactura int)
    Begin
		Delete from DetalleFactura
        where codigoDetalleFactura = _codigoDetalleFactura;
    End $$
Delimiter ;
-- ----------------------------------------------------------------------------------------------------------------------------------------------

Delimiter $$
	create function fn_CalcularPrecioUnitario(codigoProducto varchar(15)) returns decimal(10,2)
	reads sql data deterministic
    Begin
		DECLARE precioUnitario decimal(10,2);
        Select (DC.costoUnitario)
        into precioUnitario 
        from  DetalleCompra DC where DC.codigoProducto = codigoProducto;
        
        return precioUnitario + (precioUnitario * 0.40);
    End $$
Delimiter ;

Delimiter $$
	create function fn_CalcularPrecioDocena(codigoProducto varchar(15)) returns decimal(10,2)
    reads sql data deterministic
    Begin
		Declare precioDocena decimal(10,2);
        Select (DC.costoUnitario)
        into precioDocena
        from DetalleCompra DC where DC.codigoProducto = codigoProducto;
        return precioDocena + (precioDocena * 0.35);
    End $$
Delimiter ;

Delimiter $$
	create function fn_CalcularPrecioMayor(codigoProducto varchar(15)) returns decimal(10,2)
    reads sql data deterministic
    Begin
		Declare precioMayor decimal(10,2);
        Select (DC.costoUnitario)
        into precioMayor
        from DetalleCompra DC where DC.codigoProducto = codigoProducto;
        return precioMayor + (precioMayor * 0.25);
    End $$
Delimiter ;
Delimiter $$
	create procedure sp_AsignarPrecios(in codigoProducto varchar(15))
    Begin
		Declare _precioDocena decimal(10,2);
		Declare _precioUnitario decimal(10,2);
        Declare _precioMayor decimal(10,2);
        set _precioMayor = fn_CalcularPrecioMayor(codigoProducto);
        set _precioUnitario = fn_CalcularPrecioUnitario(codigoProducto);
        set _precioDocena = fn_CalcularPrecioDocena(codigoProducto);
        
        Update Productos P
        set
        P.precioUnitario = _precioUnitario,
        P.precioDocena = _precioDocena,
        P.precioMayor = _precioMayor
        where P.codigoProducto = codigoProducto; 
    End $$
Delimiter ;

Delimiter $$
	Create Trigger tr_DetalleCompra_After_Insert
    After Insert on DetalleCompra
    for each row
    Begin
        call sp_AsignarPrecios(NEW.codigoProducto);
    End $$
Delimiter ;

Delimiter $$
	create function fn_TotalCompras( numeroDocumento int)
	returns decimal(10,2)
    reads sql data deterministic
    Begin
		declare totalDocumento decimal(10,2);
        Select SUM(DC.costoUnitario * DC.cantidad) into totalDocumento from  DetalleCompra DC where DC.numeroDocumento = numeroDocumento;
		
        return totalDocumento;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_AsignarTotalCompras(in numeroDocumento int)
    Begin
		Declare _totalDocumento decimal(10,2);
        set _totalDocumento = fn_TotalCompras(numeroDocumento);
        
        Update Compras C
        set
        C.totalDocumento = _totalDocumento
        where C.numeroDocumento = numeroDocumento; 
    End $$
Delimiter ;

Delimiter $$
	Create Trigger tr_DetalleComprasTotal_After_Insert
    After Insert on DetalleCompra
    for each row
    Begin
		call sp_AsignarTotalCompras(new.numeroDocumento);
	End $$
Delimiter ;	

Delimiter $$
	create procedure sp_AgregarExistencia(codigoProducto varchar(15),cantidad int)
    Begin
		update Productos P
        set
			P.existencia = cantidad
		where P.codigoProducto = codigoProducto;
    End $$
Delimiter ;

Delimiter $$
	Create Trigger tr_DetalleCompraExistencia_After_Insert
    After Insert on DetalleCompra
    for each row
    Begin
		call sp_AgregarExistencia(NEW.codigoProducto,NEW.cantidad);
    End $$
Delimiter ;




-- -------------------------------------- Agregar ------------------------------------------------------------------------
call sp_AgregarProveedores(1,'15900126','Santi','Hernandez','Villa Nueva','Ser chancho','31657408','patoslocos.com');
call sp_AgregarTipoProducto(1,'Es una vaca lola');
call sp_AgregarTipoProducto(2,'Es un cerdota lola con mucha carne');
call sp_AgregarCompras(2,'2024/01/01','Es una vaca con mucha carne',0);
call sp_AgregarCargoEmpleado(1,'Jefatura','Ser lider de un equipo de trabajo');
call sp_AgregarCompras(1,'2024/01/01','Es una vaquita',0);
call sp_AgregarProducto('1','Es una vaca lola xD',0,0,0,'xD',1,1,1);
call sp_AgregarProducto('2','Es una vaca',0,0,0,'xD',1,1,1);
call sp_AgregarDetalleCompra(1,12.00,4,'1',1);
call sp_AgregarDetalleCompra(2,10.00,1,'2',2);
call sp_AgregarEmpleados(1,'Derian','Hernandez',2000,'Ciudad Quetzal','Vespertino',1);
call sp_AgregarFactura(1,'Feliz',0,'2024/01/01',1,1);
call sp_AgregarDetalleFactura(1,0,1,1,1);
set global time_zone = '-6:00';
-- --------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------ lIstar ------------------------------------------------------
call sp_ListarTipoProducto();
call sp_ListarProductos();
call sp_ListarCompras();
call sp_ListarDetalleFactura();
call sp_ListarEmpleados();