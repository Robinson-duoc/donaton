import React, { useState, useEffect } from 'react';

function App() {
  // Estados para alternar entre pestañas de la entrega
  const [pestana, setPestana] = useState('usuarios');
  
  // Estados de datos globales
  const [usuarios, setUsuarios] = useState([]);
  const [donaciones, setDonaciones] = useState([]);
  const [inventario, setInventario] = useState([]);
  const [totalRecaudado, setTotalRecaudado] = useState(0);
  const [mensaje, setMensaje] = useState({ texto: '', tipo: '' });

  // Formularios
  const [formUsuario, setFormUsuario] = useState({ nombre: '', email: '', password: '' });
  const [formDonacion, setFormDonacion] = useState({ usuarioId: '', monto: '' });
  const [formInventario, setFormInventario] = useState({ usuarioId: '', nombreProducto: '', categoria: 'Alimentos', cantidad: '' });

  const API_BASE = "http://localhost:8080/api";

  // Cargar datos desde la API REST de Spring Boot al iniciar
  useEffect(() => {
    cargarUsuarios();
    cargarDonaciones();
    cargarInventario();
  }, []);

  const cargarUsuarios = () => {
    fetch(`${API_BASE}/usuarios`).then(res => res.json()).then(data => setUsuarios(data)).catch(err => console.log(err));
  };

  const cargarDonaciones = () => {
    fetch(`${API_BASE}/donaciones`).then(res => res.json()).then(data => setDonaciones(data)).catch(err => console.log(err));
    fetch(`${API_BASE}/donaciones/total`).then(res => res.json()).then(data => setTotalRecaudado(data)).catch(err => console.log(err));
  };

  const cargarInventario = () => {
    fetch(`${API_BASE}/inventario`).then(res => res.json()).then(data => setInventario(data)).catch(err => console.log(err));
  };

  // Envíos de formularios por POST (JSON)
  const registrarUsuario = (e) => {
    e.preventDefault();
    fetch(`${API_BASE}/usuarios`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formUsuario)
    }).then(async res => {
      if(res.ok) {
        setMensaje({ texto: "¡Usuario registrado con éxito!", tipo: "exito" });
        setFormUsuario({ nombre: '', email: '', password: '' });
        cargarUsuarios();
      } else {
        const errText = await res.text();
        setMensaje({ texto: errText, tipo: "error" });
      }
    });
  };

  const registrarDonacion = (e) => {
    e.preventDefault();
    fetch(`${API_BASE}/donaciones`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ usuarioId: Number(formDonacion.usuarioId), monto: Number(formDonacion.monto) })
    }).then(async res => {
      if(res.ok) {
        setMensaje({ texto: "¡Donación procesada con éxito!", tipo: "exito" });
        setFormDonacion({ usuarioId: '', monto: '' });
        cargarDonaciones();
      } else {
        const errText = await res.text();
        setMensaje({ texto: errText, tipo: "error" });
      }
    });
  };

  const registrarInventario = (e) => {
    e.preventDefault();
    fetch(`${API_BASE}/inventario`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ ...formInventario, usuarioId: Number(formInventario.usuarioId), cantidad: Number(formInventario.cantidad) })
    }).then(async res => {
      if(res.ok) {
        setMensaje({ texto: "¡Insumo ingresado a bodega!", tipo: "exito" });
        setFormInventario({ usuarioId: '', nombreProducto: '', categoria: 'Alimentos', cantidad: '' });
        cargarInventario();
      } else {
        const errText = await res.text();
        setMensaje({ texto: errText, tipo: "error" });
      }
    });
  };

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', margin: '40px', backgroundColor: '#f4f4f9' }}>
      <div style={{ maxWidth: '900px', margin: '0 auto', background: 'white', padding: '30px', borderRadius: '12px', boxShadow: '0 4px 15px rgba(0,0,0,0.1)' }}>
        
        {/* Encabezado Principal */}
        <div style={{ textAlign: 'center', backgroundColor: '#007bff', color: 'white', padding: '20px', borderRadius: '8px', marginBottom: '20px' }}>
          <h1>🎁 PLATAFORMA OFICIAL DONATÓN</h1>
          <h2>Pozo Total Recaudado: ${totalRecaudado}</h2>
        </div>

        {/* Alertas */}
        {mensaje.texto && (
          <div style={{ padding: '10px', marginBottom: '15px', borderRadius: '4px', fontWeight: 'bold', backgroundColor: mensaje.tipo === 'exito' ? '#d4edda' : '#f8d7da', color: mensaje.tipo === 'exito' ? '#155724' : '#721c24' }}>
            {mensaje.texto}
          </div>
        )}

        {/* Menú de Navegación del Frontend */}
        <div style={{ display: 'flex', gap: '10px', marginBottom: '25px' }}>
          <button onClick={() => { setPestana('usuarios'); setMensaje({texto:'', tipo:''}); }} style={{ flex: 1, padding: '12px', cursor: 'pointer', backgroundColor: pestana === 'usuarios' ? '#28a745' : '#6c757d', color: 'white', border: 'none', borderRadius: '4px' }}>👥 Registro de Usuarios</button>
          <button onClick={() => { setPestana('donaciones'); setMensaje({texto:'', tipo:''}); }} style={{ flex: 1, padding: '12px', cursor: 'pointer', backgroundColor: pestana === 'donaciones' ? '#28a745' : '#6c757d', color: 'white', border: 'none', borderRadius: '4px' }}>💰 Módulo Donaciones</button>
          <button onClick={() => { setPestana('inventario'); setMensaje({texto:'', tipo:''}); }} style={{ flex: 1, padding: '12px', cursor: 'pointer', backgroundColor: pestana === 'inventario' ? '#28a745' : '#6c757d', color: 'white', border: 'none', borderRadius: '4px' }}>📦 Control de Bodega</button>
        </div>

        {/* SECCIÓN 1: USUARIOS */}
        {pestana === 'usuarios' && (
          <div>
            <h3>Formulario de Registro de Donantes</h3>
            <form onSubmit={registrarUsuario} style={{ display: 'grid', gap: '10px', marginBottom: '20px' }}>
              <input type="text" placeholder="Nombre completo" value={formUsuario.nombre} onChange={e => setFormUsuario({...formUsuario, nombre: e.target.value})} required style={{ padding: '8px' }} />
              <input type="email" placeholder="Correo electrónico" value={formUsuario.email} onChange={e => setFormUsuario({...formUsuario, email: e.target.value})} required style={{ padding: '8px' }} />
              <input type="password" placeholder="Contraseña" value={formUsuario.password} onChange={e => setFormUsuario({...formUsuario, password: e.target.value})} required style={{ padding: '8px' }} />
              <button type="submit" style={{ backgroundColor: '#007bff', color: 'white', padding: '10px', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Guardar Usuario</button>
            </form>
            <h4>Lista de Usuarios en el Sistema (API REST)</h4>
            <table width="100%" border="1" cellPadding="8" style={{ borderCollapse: 'collapse' }}>
              <thead><tr style={{ backgroundColor: '#f2f2f2' }}><th>ID</th><th>Nombre</th><th>Email</th><th>Rol</th></tr></thead>
              <tbody>
                {usuarios.map(u => <tr key={u.id}><td>{u.id}</td><td>{u.nombre}</td><td>{u.email}</td><td>{u.rol}</td></tr>)}
              </tbody>
            </table>
          </div>
        )}

        {/* SECCIÓN 2: DONACIONES */}
        {pestana === 'donaciones' && (
          <div>
            <h3>Realizar Aporte Monetario</h3>
            <form onSubmit={registrarDonacion} style={{ display: 'grid', gap: '10px', marginBottom: '20px' }}>
              <input type="number" placeholder="ID del Usuario donante (Ej: 1)" value={formDonacion.usuarioId} onChange={e => setFormDonacion({...formDonacion, usuarioId: e.target.value})} required style={{ padding: '8px' }} />
              <input type="number" placeholder="Monto a donar ($)" value={formDonacion.monto} onChange={e => setFormDonacion({...formDonacion, monto: e.target.value})} required style={{ padding: '8px' }} />
              <button type="submit" style={{ backgroundColor: '#007bff', color: 'white', padding: '10px', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Enviar Dinero</button>
            </form>
            <h4>Historial Global de Donaciones Monetarias</h4>
            <table width="100%" border="1" cellPadding="8" style={{ borderCollapse: 'collapse' }}>
              <thead><tr style={{ backgroundColor: '#f2f2f2' }}><th>ID Donación</th><th>User ID</th><th>Monto</th><th>Estado</th></tr></thead>
              <tbody>
                {donaciones.map(d => <tr key={d.id}><td>{d.id}</td><td>{d.usuarioId}</td><td>${d.monto}</td><td>{d.estado}</td></tr>)}
              </tbody>
            </table>
          </div>
        )}

        {/* SECCIÓN 3: INVENTARIO */}
        {pestana === 'inventario' && (
          <div>
            <h3>Ingreso de Donaciones Materiales a Bodega</h3>
            <form onSubmit={registrarInventario} style={{ display: 'grid', gap: '10px', marginBottom: '20px' }}>
              <input type="number" placeholder="ID del Donante (Usuario)" value={formInventario.usuarioId} onChange={e => setFormInventario({...formInventario, usuarioId: e.target.value})} required style={{ padding: '8px' }} />
              <input type="text" placeholder="Nombre del Producto (Ej: Frazadas)" value={formInventario.nombreProducto} onChange={e => setFormInventario({...formInventario, nombreProducto: e.target.value})} required style={{ padding: '8px' }} />
              <select value={formInventario.categoria} onChange={e => setFormInventario({...formInventario, categoria: e.target.value})} style={{ padding: '8px' }}>
                <option value="Alimentos">Alimentos No Perecederos</option>
                <option value="Ropa">Ropa y Abrigo</option>
                <option value="Medicamentos">Medicamentos</option>
                <option value="Otros">Otros</option>
              </select>
              <input type="number" placeholder="Cantidad (Unidades)" value={formInventario.cantidad} onChange={e => setFormInventario({...formInventario, cantidad: e.target.value})} required style={{ padding: '8px' }} />
              <button type="submit" style={{ backgroundColor: '#007bff', color: 'white', padding: '10px', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Ingresar Producto</button>
            </form>
            <h4>Stock en Bodega en Tiempo Real</h4>
            <table width="100%" border="1" cellPadding="8" style={{ borderCollapse: 'collapse' }}>
              <thead><tr style={{ backgroundColor: '#f2f2f2' }}><th>ID Ítem</th><th>User ID</th><th>Producto</th><th>Categoría</th><th>Cantidad</th></tr></thead>
              <tbody>
                {inventario.map(i => <tr key={i.id}><td>{i.id}</td><td>{i.usuarioId}</td><td>{i.nombreProducto}</td><td>{i.categoria}</td><td>{i.cantidad} u</td></tr>)}
              </tbody>
            </table>
          </div>
        )}

      </div>
    </div>
  );
}

export default App;