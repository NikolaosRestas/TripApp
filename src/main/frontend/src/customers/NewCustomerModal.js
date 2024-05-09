import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

export default function NewCustomerModal({isOpen, onClose, onSave}) {
    const [customer, setCustomer] = useState({name: ""});
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [officesData, setOfficesData] = useState([]);


    useEffect(() => {
        fetch('/offices')
            .then(response => response.json())
            .then(data => {
                setOfficesData(data);
            })
            .catch(error => {
                console.error('Error fetching offices:', error);
            });
    }, []);

    useEffect(() => {
        setCustomer({...customer}); // Update local state when the clientData prop changes
    }, [customer]);

    const handleSave = () => {

         if (!isEmailValid(customer.email)) {
              alert('Invalid email address');
              return;
        }

        if (!isPhoneNumberValid(customer.phone)) {
              alert('Invalid phone number (must be 10 digits)');
              return;
        }


        fetch(`/customers/add`,
            {
                method: 'POST',
                body: JSON.stringify(customer),
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then((data) => {
                onSave(data);
                setIsSuccessAlertOpen(true);
                setTimeout(() => {
                    setIsSuccessAlertOpen(false);
                }, 5000);
            })
            .catch((error) => {
                console.error('Error while calling the API:', error);
            });

        console.log("Save changes:", customer);
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };

    const handleCancel = () => {
        onClose(); // Close the modal without saving.
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setCustomer((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

     const isEmailValid = (email) => {
            const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            return emailPattern.test(email);
          };

        const isPhoneNumberValid = (phone) => {
            const phonePattern = /^\d{10}$/;
            return phonePattern.test(phone);
          };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New Customer</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Customer Name"
                        name="name"
                        value={customer.name}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Email"
                        name="email"
                        value={customer.email}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Phone"
                        name="phone"
                        value={customer.phone}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Office"
                        name="officeId"
                        value={customer.officeId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Office"
                        name="officeId"
                        value={customer.officeId || 'default'}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="default" disabled>Select an Office </MenuItem>
                        {
                            officesData.map((office) => (
                                <MenuItem key={office.id} value={office.id}> {office.name} </MenuItem>))
                        }
                    </Select>

                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCancel}>Cancel</Button>
                    <Button onClick={handleSave} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </Dialog>

            <div className="relative h-32 flex flex-nowrap">
                <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                    {isSuccessAlertOpen && <Alert severity="success">The customer added successfully!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}