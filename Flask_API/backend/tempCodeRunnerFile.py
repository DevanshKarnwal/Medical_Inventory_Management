@app.route('/docs', methods = ['GET'])
def docs():
    return render_template('docs.html')