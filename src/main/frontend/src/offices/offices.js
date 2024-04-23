import React, { useEffect, useState } from 'react';
import OfficesTableComponent from './OfficesTableComponent';
import { Button } from '@mui/material';
import NewOfficeModal from './NewOfficeModal';
import Loader from "../loader/loader";

const OfficesPage = () => {
    const [officesData, setOfficesData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isNewOfficeModalOpen, setNewOfficeModalOpen] = useState(false);

    useEffect(() => {
        fetch('/offices')
            .then(response => response.json())
            .then(data => {
                setOfficesData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Error fetching counties:', error);
                setIsLoading(false); // Ensure loading state is updated even on error
            });
    }, []);

    const newOffice = () => {
        setNewOfficeModalOpen(true);
    };

    const handleCloseNewOfficeModal = () => {
        setNewOfficeModalOpen(false);
    };

    const handleSaveNewOffice = office => {
        setOfficesData(prevOffices => [...prevOffices, office]);
        handleCloseNewOfficeModal();
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Offices</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newOffice}>
                        New Office
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <OfficesTableComponent offices={officesData} onChange={setOfficesData} />
                )}
            </div>

            {isNewOfficeModalOpen && (
                <NewOfficeModal
                    isOpen={isNewOfficeModalOpen}
                    onClose={handleCloseNewOfficeModal}
                    onSave={handleSaveNewOffice}
                />
            )}
        </div>
    );
};

export default OfficesPage;