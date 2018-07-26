from flask import Flask,request
from subprocess import call
app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def display():
	print request.form
	print request.args
	with open("myfile","a") as myfile:
		#myfile.write((request.form).values()[0])
		myfile.write("\n")
	#f = open('myfile','w')
	#f.write((request.form).values()[0])
	#f.write('\n')
	myfile.close()
	call(["./density"])
	call(["./a.out"])
	return "Looks like it works!"
	


if __name__=='__main__':
	app.run(host='0.0.0.0',debug=False,port=3134)
	
	
	
	
