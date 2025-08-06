import oracledb

conn = oracledb.connect(user='rm559617', password='180794', dsn='oracle.fiap.com.br/orcl')

cur = conn.cursor()

cur.close()
conn.close()