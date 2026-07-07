import React, { useState, useEffect } from 'react';

function App() {
  // Navegación principal de la página
  const [vistaActual, setVistaActual] = useState('inicio');
  
  // Navegación interna del Dashboard
  const [pestanaDashboard, setPestanaDashboard] = useState('usuarios');
  
  // Estados de datos globales (API)
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

  // Cargar datos al montar el componente
  useEffect(() => {
    cargarUsuarios();
    cargarDonaciones();
    cargarInventario();
  }, []);

  // --- LÓGICA DE CONEXIÓN API REST ---
  const cargarUsuarios = () => fetch(`${API_BASE}/usuarios`).then(res => res.json()).then(data => setUsuarios(data)).catch(err => console.log(err));
  const cargarDonaciones = () => {
    fetch(`${API_BASE}/donaciones`).then(res => res.json()).then(data => setDonaciones(data)).catch(err => console.log(err));
    fetch(`${API_BASE}/donaciones/total`).then(res => res.json()).then(data => setTotalRecaudado(data)).catch(err => console.log(err));
  };
  const cargarInventario = () => fetch(`${API_BASE}/inventario`).then(res => res.json()).then(data => setInventario(data)).catch(err => console.log(err));

  const registrarUsuario = (e) => {
    e.preventDefault();
    fetch(`${API_BASE}/usuarios`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(formUsuario) })
    .then(async res => {
      if(res.ok) { setMensaje({ texto: "¡Usuario registrado!", tipo: "exito" }); setFormUsuario({ nombre: '', email: '', password: '' }); cargarUsuarios(); } 
      else { setMensaje({ texto: await res.text(), tipo: "error" }); }
    });
  };

  const registrarDonacion = (e) => {
    e.preventDefault();
    fetch(`${API_BASE}/donaciones`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ usuarioId: Number(formDonacion.usuarioId), monto: Number(formDonacion.monto) }) })
    .then(async res => {
      if(res.ok) { setMensaje({ texto: "¡Donación procesada!", tipo: "exito" }); setFormDonacion({ usuarioId: '', monto: '' }); cargarDonaciones(); } 
      else { setMensaje({ texto: await res.text(), tipo: "error" }); }
    });
  };

  const registrarInventario = (e) => {
    e.preventDefault();
    fetch(`${API_BASE}/inventario`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ ...formInventario, usuarioId: Number(formInventario.usuarioId), cantidad: Number(formInventario.cantidad) }) })
    .then(async res => {
      if(res.ok) { setMensaje({ texto: "¡Insumo ingresado!", tipo: "exito" }); setFormInventario({ usuarioId: '', nombreProducto: '', categoria: 'Alimentos', cantidad: '' }); cargarInventario(); } 
      else { setMensaje({ texto: await res.text(), tipo: "error" }); }
    });
  };

  // --- COMPONENTES VISUALES ---

  const renderInicio = () => (
    <div style={{ textAlign: 'center', padding: '50px 20px' }}>
      <h1 style={{ fontSize: '3rem', color: '#1a365d' }}>Bienvenido a la Donatón</h1>
      <p style={{ fontSize: '1.2rem', color: '#4a5568', maxWidth: '600px', margin: '0 auto 30px' }}>
        Plataforma oficial para la gestión transparente de recursos, donaciones monetarias y control de bodega en tiempo real. Ayúdanos a llegar a la meta.
      </p>
      <div style={{ backgroundColor: '#ebf8ff', padding: '30px', borderRadius: '12px', display: 'inline-block', border: '2px solid #3182ce' }}>
        <h2 style={{ color: '#2b6cb0', margin: '0 0 10px 0' }}>Meta Actual Recaudada</h2>
        <p style={{ fontSize: '3rem', fontWeight: 'bold', color: '#2c5282', margin: 0 }}>${totalRecaudado}</p>
      </div>
      <br/>
      <button onClick={() => setVistaActual('dashboard')} style={{ marginTop: '40px', padding: '15px 30px', fontSize: '1.1rem', backgroundColor: '#3182ce', color: 'white', border: 'none', borderRadius: '8px', cursor: 'pointer', fontWeight: 'bold' }}>
        Ir al Panel de Gestión ➔
      </button>
    </div>
  );

  const renderContacto = () => (
    <div style={{ maxWidth: '600px', margin: '0 auto', padding: '40px 20px' }}>
      <h2 style={{ color: '#1a365d', borderBottom: '2px solid #e2e8f0', paddingBottom: '10px' }}>Contáctanos</h2>
      <p style={{ color: '#4a5568', marginBottom: '30px' }}>¿Tienes problemas con tu donación o dudas sobre la plataforma? Escríbenos.</p>
      <form onSubmit={(e) => { e.preventDefault(); alert('Mensaje enviado al equipo de soporte.'); }} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
        <input type="text" placeholder="Tu Nombre" required style={{ padding: '12px', borderRadius: '6px', border: '1px solid #cbd5e0' }} />
        <input type="email" placeholder="Tu Correo" required style={{ padding: '12px', borderRadius: '6px', border: '1px solid #cbd5e0' }} />
        <textarea placeholder="¿En qué podemos ayudarte?" rows="5" required style={{ padding: '12px', borderRadius: '6px', border: '1px solid #cbd5e0' }}></textarea>
        <button type="submit" style={{ padding: '15px', backgroundColor: '#2f855a', color: 'white', border: 'none', borderRadius: '6px', cursor: 'pointer', fontWeight: 'bold' }}>
          Enviar Mensaje
        </button>
      </form>
    </div>
  );

  const renderDashboard = () => (
    <div style={{ maxWidth: '1000px', margin: '0 auto', background: 'white', padding: '30px', borderRadius: '12px', boxShadow: '0 4px 15px rgba(0,0,0,0.05)' }}>
      {/* Alertas */}
      {mensaje.texto && (
        <div style={{ padding: '15px', marginBottom: '20px', borderRadius: '6px', fontWeight: 'bold', backgroundColor: mensaje.tipo === 'exito' ? '#c6f6d5' : '#fed7d7', color: mensaje.tipo === 'exito' ? '#22543d' : '#742a2a' }}>
          {mensaje.texto}
        </div>
      )}

      {/* Menú Interno del Dashboard */}
      <div style={{ display: 'flex', gap: '10px', marginBottom: '30px' }}>
        <button onClick={() => { setPestanaDashboard('usuarios'); setMensaje({texto:'', tipo:''}); }} style={{ flex: 1, padding: '15px', cursor: 'pointer', fontWeight: 'bold', backgroundColor: pestanaDashboard === 'usuarios' ? '#2b6cb0' : '#e2e8f0', color: pestanaDashboard === 'usuarios' ? 'white' : '#4a5568', border: 'none', borderRadius: '8px' }}>👥 Usuarios</button>
        <button onClick={() => { setPestanaDashboard('donaciones'); setMensaje({texto:'', tipo:''}); }} style={{ flex: 1, padding: '15px', cursor: 'pointer', fontWeight: 'bold', backgroundColor: pestanaDashboard === 'donaciones' ? '#2b6cb0' : '#e2e8f0', color: pestanaDashboard === 'donaciones' ? 'white' : '#4a5568', border: 'none', borderRadius: '8px' }}>💰 Donaciones</button>
        <button onClick={() => { setPestanaDashboard('inventario'); setMensaje({texto:'', tipo:''}); }} style={{ flex: 1, padding: '15px', cursor: 'pointer', fontWeight: 'bold', backgroundColor: pestanaDashboard === 'inventario' ? '#2b6cb0' : '#e2e8f0', color: pestanaDashboard === 'inventario' ? 'white' : '#4a5568', border: 'none', borderRadius: '8px' }}>📦 Bodega</button>
      </div>

      {/* Vistas del Dashboard */}
      {pestanaDashboard === 'usuarios' && (
        <div>
          <h3 style={{ color: '#2d3748' }}>Registro de Usuarios</h3>
          <form onSubmit={registrarUsuario} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr auto', gap: '10px', marginBottom: '20px' }}>
            <input type="text" placeholder="Nombre" value={formUsuario.nombre} onChange={e => setFormUsuario({...formUsuario, nombre: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <input type="email" placeholder="Email" value={formUsuario.email} onChange={e => setFormUsuario({...formUsuario, email: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <input type="password" placeholder="Clave" value={formUsuario.password} onChange={e => setFormUsuario({...formUsuario, password: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <button type="submit" style={{ backgroundColor: '#2f855a', color: 'white', padding: '10px 20px', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Guardar</button>
          </form>
          <table width="100%" cellPadding="12" style={{ borderCollapse: 'collapse', textAlign: 'left', backgroundColor: '#f7fafc', borderRadius: '8px', overflow: 'hidden' }}>
            <thead style={{ backgroundColor: '#edf2f7' }}><tr><th>ID</th><th>Nombre</th><th>Email</th><th>Rol</th></tr></thead>
            <tbody>{usuarios.map(u => <tr key={u.id} style={{ borderBottom: '1px solid #e2e8f0' }}><td>{u.id}</td><td>{u.nombre}</td><td>{u.email}</td><td>{u.rol}</td></tr>)}</tbody>
          </table>
        </div>
      )}

      {pestanaDashboard === 'donaciones' && (
        <div>
          <h3 style={{ color: '#2d3748' }}>Registrar Aporte Financiero</h3>
          <form onSubmit={registrarDonacion} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr auto', gap: '10px', marginBottom: '20px' }}>
            <input type="number" placeholder="ID Usuario" value={formDonacion.usuarioId} onChange={e => setFormDonacion({...formDonacion, usuarioId: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <input type="number" placeholder="Monto ($)" value={formDonacion.monto} onChange={e => setFormDonacion({...formDonacion, monto: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <button type="submit" style={{ backgroundColor: '#2f855a', color: 'white', padding: '10px 20px', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Aportar</button>
          </form>
          <table width="100%" cellPadding="12" style={{ borderCollapse: 'collapse', textAlign: 'left', backgroundColor: '#f7fafc', borderRadius: '8px', overflow: 'hidden' }}>
            <thead style={{ backgroundColor: '#edf2f7' }}><tr><th>ID Tx</th><th>User ID</th><th>Monto</th><th>Estado</th></tr></thead>
            <tbody>{donaciones.map(d => <tr key={d.id} style={{ borderBottom: '1px solid #e2e8f0' }}><td>{d.id}</td><td>{d.usuarioId}</td><td style={{fontWeight:'bold', color:'#2f855a'}}>${d.monto}</td><td>{d.estado}</td></tr>)}</tbody>
          </table>
        </div>
      )}

      {pestanaDashboard === 'inventario' && (
        <div>
          <h3 style={{ color: '#2d3748' }}>Ingreso a Bodega</h3>
          <form onSubmit={registrarInventario} style={{ display: 'grid', gridTemplateColumns: '1fr 2fr 1fr 1fr auto', gap: '10px', marginBottom: '20px' }}>
            <input type="number" placeholder="ID User" value={formInventario.usuarioId} onChange={e => setFormInventario({...formInventario, usuarioId: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <input type="text" placeholder="Producto" value={formInventario.nombreProducto} onChange={e => setFormInventario({...formInventario, nombreProducto: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <select value={formInventario.categoria} onChange={e => setFormInventario({...formInventario, categoria: e.target.value})} style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }}>
              <option value="Alimentos">Alimentos</option>
              <option value="Ropa">Ropa</option>
              <option value="Medicamentos">Medicamentos</option>
            </select>
            <input type="number" placeholder="Cant." value={formInventario.cantidad} onChange={e => setFormInventario({...formInventario, cantidad: e.target.value})} required style={{ padding: '10px', border: '1px solid #cbd5e0', borderRadius: '4px' }} />
            <button type="submit" style={{ backgroundColor: '#2f855a', color: 'white', padding: '10px 20px', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Ingresar</button>
          </form>
          <table width="100%" cellPadding="12" style={{ borderCollapse: 'collapse', textAlign: 'left', backgroundColor: '#f7fafc', borderRadius: '8px', overflow: 'hidden' }}>
            <thead style={{ backgroundColor: '#edf2f7' }}><tr><th>ID</th><th>User ID</th><th>Producto</th><th>Cat.</th><th>Stock</th></tr></thead>
            <tbody>{inventario.map(i => <tr key={i.id} style={{ borderBottom: '1px solid #e2e8f0' }}><td>{i.id}</td><td>{i.usuarioId}</td><td>{i.nombreProducto}</td><td>{i.categoria}</td><td>{i.cantidad} u</td></tr>)}</tbody>
          </table>
        </div>
      )}
    </div>
  );

  return (
    <div style={{ fontFamily: 'system-ui, -apple-system, sans-serif', minHeight: '100vh', display: 'flex', flexDirection: 'column', backgroundColor: '#f4f7f6' }}>
      
      {/* BARRA DE NAVEGACIÓN (HEADER) */}
      <nav style={{ backgroundColor: '#1a365d', padding: '15px 40px', display: 'flex', justifyContent: 'space-between', alignItems: 'center', boxShadow: '0 2px 10px rgba(0,0,0,0.1)' }}>
        <div style={{ color: 'white', fontSize: '1.5rem', fontWeight: 'bold', cursor: 'pointer' }} onClick={() => setVistaActual('inicio')}>
          🎁 DonatónApp
        </div>
        <div style={{ display: 'flex', gap: '20px' }}>
          <button onClick={() => setVistaActual('inicio')} style={{ background: 'none', border: 'none', color: vistaActual === 'inicio' ? '#63b3ed' : 'white', fontSize: '1rem', cursor: 'pointer', fontWeight: vistaActual === 'inicio' ? 'bold' : 'normal' }}>Inicio</button>
          <button onClick={() => setVistaActual('dashboard')} style={{ background: 'none', border: 'none', color: vistaActual === 'dashboard' ? '#63b3ed' : 'white', fontSize: '1rem', cursor: 'pointer', fontWeight: vistaActual === 'dashboard' ? 'bold' : 'normal' }}>Panel de Gestión</button>
          <button onClick={() => setVistaActual('contacto')} style={{ background: 'none', border: 'none', color: vistaActual === 'contacto' ? '#63b3ed' : 'white', fontSize: '1rem', cursor: 'pointer', fontWeight: vistaActual === 'contacto' ? 'bold' : 'normal' }}>Contacto</button>
        </div>
      </nav>

      {/* CONTENIDO PRINCIPAL DINÁMICO */}
      <main style={{ flex: 1, padding: '40px 20px' }}>
        {vistaActual === 'inicio' && renderInicio()}
        {vistaActual === 'dashboard' && renderDashboard()}
        {vistaActual === 'contacto' && renderContacto()}
      </main>

      {/* PIE DE PÁGINA (FOOTER) */}
      <footer style={{ backgroundColor: '#2d3748', color: '#a0aec0', textAlign: 'center', padding: '20px', fontSize: '0.9rem' }}>
        <p>© 2026 Plataforma Donatón. Todos los derechos reservados.</p>
        <p>Sistema de Gestión de Eventos Benéficos - Arquitectura de Microservicios</p>
      </footer>

    </div>
  );
}

export default App;