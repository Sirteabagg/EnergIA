import sys
import os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../')))

import pytest
from scripts.manage import manage_data

def test_clean_data():
    # Exemple de test basique pour vérifier si la fonction clean_data existe
    assert hasattr(manage_data, 'clean_data')