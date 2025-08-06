from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
# Ajuste a string de conexão conforme seu ambiente Oracle
app.config['SQLALCHEMY_DATABASE_URI'] = 'oracle+cx_oracle://usuario:senha@host:porta/servico'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

# Models
class Pessoa(db.Model):
    __tablename__ = 'tdspr_pessoa'
    id = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    telefone = db.Column(db.String(20), unique=True)
    cpf = db.Column(db.String(14), unique=True)
    enderecos = db.relationship('Endereco', backref='pessoa', cascade="all, delete-orphan", lazy=True)

class Endereco(db.Model):
    __tablename__ = 'tdspr_endereco'
    id = db.Column(db.Integer, primary_key=True)
    logradouro = db.Column(db.String(100), nullable=False)
    complemento = db.Column(db.String(100))
    numero = db.Column(db.String(10), nullable=False)
    bairro = db.Column(db.String(50), nullable=False)
    cep = db.Column(db.String(10), nullable=False)
    municipio = db.Column(db.String(50), nullable=False)
    pessoa_id = db.Column(db.Integer, db.ForeignKey('tdspr_pessoa.id'), nullable=False)

# Criação das tabelas (apenas para referência; em produção, use migrações)
# db.create_all()

@app.route('/pessoa', methods=['POST'])
def criar_pessoa():
    data = request.get_json()
    endereco_data = data.get('endereco')
    if not data.get('nome') or not endereco_data:
        return jsonify({'error': 'Nome e endereço são obrigatórios'}), 400

    try:
        pessoa = Pessoa(
            nome=data['nome'],
            telefone=data.get('telefone'),
            cpf=data.get('cpf')
        )
        endereco = Endereco(
            logradouro=endereco_data['logradouro'],
            complemento=endereco_data.get('complemento'),
            numero=endereco_data['numero'],
            bairro=endereco_data['bairro'],
            cep=endereco_data['cep'],
            municipio=endereco_data['municipio'],
            pessoa=pessoa
        )
        db.session.add(pessoa)
        db.session.add(endereco)
        db.session.commit()
        return jsonify({'message': 'Pessoa e endereço criados com sucesso', 'pessoa_id': pessoa.id}), 201
    except Exception as e:
        db.session.rollback()
        return jsonify({'error': str(e)}), 500

@app.route('/pessoa/<int:pessoa_id>', methods=['GET'])
def obter_pessoa(pessoa_id):
    pessoa = Pessoa.query.get(pessoa_id)
    if not pessoa:
        return jsonify({'error': 'Pessoa não encontrada'}), 404

    enderecos = [{
        'id': end.id,
        'logradouro': end.logradouro,
        'complemento': end.complemento,
        'numero': end.numero,
        'bairro': end.bairro,
        'cep': end.cep,
        'municipio': end.municipio
    } for end in pessoa.enderecos]

    return jsonify({
        'id': pessoa.id,
        'nome': pessoa.nome,
        'telefone': pessoa.telefone,
        'cpf': pessoa.cpf,
        'enderecos': enderecos
    }), 200

if __name__ == '__main__':
    app.run(debug=True)
