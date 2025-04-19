from flask import Flask , jsonify, request, render_template
from createTableOperation import createTable
from addOperation import createUser, createProduct,createOrderDetails,createSellHistory,createAvailableProducts
from authUser import authenticate_user
from readOperation import getAllUsers, getSpecificUser, getAllProducts, getSpecificProduct , getAllOrderDetails, getSpecificOrderDetails,getAllSellHistory,getSpecificSellHistory , getAllAvailableProducts, getSpecificAvailableProduct
from updateOperation import update_approve_user,update_block_user, updateUserAllDetails, updateProduct,updateOrderApproval,updateOrderDetails,updateSellHistory,updateAvailableProducts

from deleteOperation import deleteUser, deleteProduct, deleteOrderDetails, deleteSellHistory, deleteAvailableProducts


app = Flask(__name__)

@app.route('/docs', methods = ['GET'])
def docs():
    return render_template('docs.html')

@app.route('/')
def hello_world():
    return 'Hello, World! from vscode'

#define end point
@app.route('/user',methods = ['GET'])
def hello():
    return jsonify({'name' : 'Devansh','phone' : 9458787387})


# for sign up
@app.route('/createUser',methods = ['POST'])
def create_user():    
    try:
        name = request.form['name']
        password = request.form['password']
        phoneNumber = request.form['phoneNumber']
        email = request.form['email']
        pincode = request.form['pincode']
        address = request.form['address']
        response = createUser(name = name, password = password, phoneNumber = phoneNumber, email = email, pincode = pincode, address = address)
        return response
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})


@app.route('/login',methods = ['POST'])
def login_user():
    try:
        email = request.form['email']
        password = request.form['password']
        user = authenticate_user(email,password)
        if user:
            return jsonify({"message" : user[1], "status" : 200})
        else:
            return jsonify({"message" : "Invalid email or password", "status" : 401})
    
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})    
    
    
# this is to get all users , and for admin side
@app.route('/getAllUsers',methods = ['GET'])
def get_All_Users():
    try:
        users = getAllUsers()
        if users:
            return jsonify({"message" : users, "status" : 200})
        else:
            return jsonify({"message" : "No users found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})


@app.route('/getSpecificUser',methods = ['POST'])
def get_Specific_User():
    try:
        userId = request.form['user_id']
        user = getSpecificUser(userId)
        if user:
            return jsonify({"message" : user, "status" : 200})
        else:
            return jsonify({"message" : "No user found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400}) 
    

@app.route('/approveUser',methods = ['PATCH'])
def approve_User():
    try:
        user_id = request.form['user_id']
        approve = request.form['approve']
        update_approve_user(user_id, approve)
        return jsonify({"message" : "User approval status updated", "status" : 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

@app.route('/blockUser',methods = ['PATCH'])
def block_User():
    try:
        user_id = request.form['user_id']
        block = request.form['block']
        update_block_user(user_id, block)
        return jsonify({"message" : "User block status updated", "status" : 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})
    
@app.route('/deleteUser',methods = ['DELETE'])
def delete_User():
    try:
        user_id = request.form['user_id']
        deleted = deleteUser(userId=user_id)
        return deleted
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})



# update user all details
@app.route('/updateUserAllDetails', methods = ['PATCH'])
def update_User_All_Details():
    try:
        user_id = request.form['user_id']
        name = request.form['name']
        address = request.form['address']
        email = request.form['email']
        phoneNumber = request.form['phoneNumber']
        pincode = request.form['pincode']
        password = request.form['password']
        updateUserAllDetails(userId=user_id,name=name,address=address,email=email,phoneNumber=phoneNumber,pincode=pincode,password=password )
        return jsonify({"message": "User details updated", "status": 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})


@app.route('/updateUserAllDetails', methods = ['PATCH'])
def update_User_All_Details2():
    try:
        
        updateUser = {}
        for key, value in request.form.items():
            if key != 'user_id':
                updateUser[key] = value
        for key,value in updateUser.items():
            if key == 'name':
                name = value
            elif key == 'address':
                address = value
            elif key == 'email':
                email = value
            elif key == 'phoneNumber':
                phoneNumber = value
            elif key == 'pincode':
                pincode = value
            elif key == 'password':
                password = value
        return jsonify({"message": "User details updated", "status": 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})



# PRODUCT ROUTES

@app.route('/createProduct', methods = ['POST'])
def create_Product():
    try:
        name = request.form['name']
        price = request.form['price']
        category = request.form['category']
        stock = request.form['stock']
        product_id = createProduct(name=name,price=price,category=category,stock=stock)
        return product_id
    except Exception as error:
        return jsonify({"message" : error , "status" : 400})


@app.route('/getAllProducts',methods = ['GET'])
def get_All_Products():
    try:
        products = getAllProducts()
        if products:
            return jsonify({"message" : products, "status" : 200})
        else:
            return jsonify({"message" : "No product found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})


@app.route('/getSpecificProduct',methods = ['POST'])
def get_Specific_Products():
    try:
        productId = request.form['product_id']
        product = getSpecificProduct(productId)
        if product:
            return jsonify({"message" : product, "status" : 200})
        else:
            return jsonify({"message" : "No product found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400}) 
    
@app.route('/updateProduct', methods = ['PATCH'])
def update_Product():
    try:
        product_id = request.form['product_id']
        name = request.form['name']
        price = request.form['price']
        category = request.form['category']
        stock = request.form['stock']
        updateProduct(productId=product_id,name=name,price=price,category=category,stock=stock )
        return jsonify({"message": "Product details updated", "status": 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

@app.route('/deleteProduct',methods = ['DELETE'])
def delete_Product():
    try:
        product_id = request.form['product_id']
        deleted = deleteProduct(productId=product_id)
        return deleted
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

# ORDER DETAILS
@app.route('/createOrderDetails', methods = ['POST'])
def create_Order_Details():
    try:
        user_id = request.form['user_id']
        product_id = request.form['product_id']
        isApproved = request.form['isApproved']
        quantity = request.form['quantity']
        price = request.form['price']
        total_amount = float(price) * int(quantity)
        product_name = request.form['product_name']
        user_name = request.form['user_name']
        message = request.form['message']
        category = request.form['category']
        order_id = createOrderDetails(user_id=user_id,product_id=product_id,isApproved=isApproved,quantity=quantity,price=price,total_amount=total_amount,product_name=product_name,user_name=user_name,message=message,category=category)
        return order_id
    except Exception as error:
        return jsonify({"message" : error , "status" : 400})

@app.route('/getAllOrderDetails',methods = ['GET'])
def get_All_Order_Details():
    try:
        orderDetails = getAllOrderDetails()
        if orderDetails:
            return jsonify({"message" : orderDetails, "status" : 200})
        else:
            return jsonify({"message" : "No order found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})

@app.route('/getSpecificOrderDetails',methods = ['POST'])
def get_Specific_Order_Details():
    try:
        orderId = request.form['order_id']
        orderDetails = getSpecificOrderDetails(orderId)
        if orderDetails:
            return jsonify({"message" : orderDetails, "status" : 200})
        else:
            return jsonify({"message" : "No order found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})



@app.route('/updateOrderApproval', methods = ['PATCH'])
def update_Order_Approval():
    try:
        order_id = request.form['order_id']
        isApproved = request.form['isApproved']
        updateOrderApproval(orderId=order_id,isApproved=isApproved )
        return jsonify({"message": "Order approval status updated", "status": 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})


@app.route('/updateOrderDetails', methods = ['PATCH'])
def update_Order_Details():
    try:
        user_id = request.form['user_id']
        product_id = request.form['product_id']
        quantity = request.form['quantity']
        price = request.form['price']
        message = request.form['message']
        category = request.form['category']
        orderId = request.form['order_id']
        status = updateOrderDetails(user_id=user_id,product_id=product_id,quantity=quantity,price=price,message=message,category=category, orderId=orderId)
        return status
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})
    
@app.route('/deleteOrderDetails',methods = ['DELETE'])
def delete_Order_Details():
    try:
        order_id = request.form['order_id']
        deleted = deleteOrderDetails(orderId=order_id)
        return deleted
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

# sell history 
@app.route('/createSellHistory', methods = ['POST'])
def create_Sell_History():
    try:
        product_id = request.form['product_id']
        quantity = request.form['quantity']
        remaning_stock = request.form['remaning_stock']
        total_amount = request.form['total_amount']
        price = request.form['price']
        product_name = request.form['product_name']
        user_id = request.form['user_id']
        user_name = request.form['user_name']
        sell_id = createSellHistory(product_id=product_id,quantity=quantity,remaning_stock=remaning_stock,total_amount=total_amount,price=price,product_name=product_name,user_id=user_id,user_name=user_name)
        return sell_id
    except Exception as error:
        return jsonify({"message" : error , "status" : 400})

@app.route('/getAllSellHistory',methods = ['GET'])
def get_All_Sell_History():
    try:
        sellHistory = getAllSellHistory()
        if sellHistory:
            return jsonify({"message" : sellHistory, "status" : 200})
        else:
            return jsonify({"message" : "No sell history found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})

@app.route('/getSpecificSellHistory',methods = ['POST'])
def get_Specific_Sell_History():
    try:
        sellId = request.form['sell_id']
        sellHistory = getSpecificSellHistory(sellId)
        if sellHistory:
            return jsonify({"message" : sellHistory, "status" : 200})
        else:
            return jsonify({"message" : "No sell history found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})
    
@app.route('/updateSellHistory', methods = ['PATCH'])
def update_Sell_History():
    try:
        sell_id = request.form['sell_id']
        quantity = request.form['quantity']
        remaning_stock = request.form['remaning_stock']
        total_amount = request.form['total_amount']
        price = request.form['price']
        updateSellHistory(sell_id=sell_id,quantity=quantity,remaning_stock=remaning_stock,total_amount=total_amount,price=price )
        return jsonify({"message": "Sell history updated", "status": 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

@app.route('/deleteSellHistory',methods = ['DELETE'])
def delete_Sell_History():
    try:
        sell_id = request.form['sell_id']
        deleted = deleteSellHistory(sellHistoryId=sell_id)
        return deleted
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

# Available Products
@app.route('/createAvailableProducts', methods = ['POST'])
def create_Available_Products():
    try:
        name = request.form['name']
        price = request.form['price']
        category = request.form['category']
        stock = request.form['stock']
        user_id = request.form['user_id']
        user_name = request.form['user_name']
        product_id = createAvailableProducts(name=name,price=price,category=category,stock=stock,user_id=user_id,user_name=user_name)
        return product_id
    except Exception as error:
        return jsonify({"message" : error , "status" : 400})


@app.route('/getAvailableProducts',methods = ['GET'])
def get_Available_Products():
    try:
        availableProducts = getAllAvailableProducts()
        if availableProducts:
            return jsonify({"message" : availableProducts, "status" : 200})
        else:
            return jsonify({"message" : "No product found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})

@app.route('/getSpecificAvailableProducts',methods = ['POST'])
def get_Specific_Available_Products():
    try:
        productId = request.form['product_id']
        product = getSpecificAvailableProduct(productId)
        if product:
            return jsonify({"message" : product, "status" : 200})
        else:
            return jsonify({"message" : "No product found", "status" : 404})
                
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

@app.route('/updateAvailableProducts', methods = ['PATCH'])
def update_Available_Products():
    try:
        product_id = request.form['product_id']
        name = request.form['name']
        price = request.form['price']
        category = request.form['category']
        stock = request.form['stock']
        updateAvailableProducts(product_id=product_id,name=name,price=price,category=category,stock=stock )
        return jsonify({"message": "Product details updated", "status": 200})
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})

@app.route('/deleteAvailableProducts',methods = ['DELETE'])
def delete_Available_Products():
    try:
        product_id = request.form['product_id']
        deleted = deleteAvailableProducts(productId=product_id)
        return deleted
    except Exception as error:
        return jsonify({"message" : str(error), "status" : 400})


if __name__ == '__main__':
    createTable()
    app.run(debug=True)
